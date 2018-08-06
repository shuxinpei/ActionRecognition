from flask import Flask
from flask.ext.sqlalchemy import SQLAlchemy
import config
from config import db

class User(db.Model):
    __tablename__ = 'user'

    UserId = db.Column(db.Integer, primary_key=True, nullable=False)
    UserName = db.Column(db.String(16), nullable=False)
    Password = db.Column(db.String(16), nullable=False)

    def __init__(self, UserId, UserName, Password):
        self.UserId = UserId
        self.UserName = UserName
        self.Password = Password

    def __repr__(self):
        return '' % (self.UserId, self.UserName,self.Password)

class Action(db.Model):
    __tablename__ = 'action'

    UserId = db.Column(db.Integer, primary_key=True, nullable=False)
    actionId = db.Column(db.Integer, primary_key=True,nullable= False)
    actionCate = db.Column(db.Integer,nullable= False)
    number = db.Column(db.Integer,nullable= False)
    standnumber = db.Column(db.Integer,nullable= False)
    energy = db.Column(db.Integer,nullable= False)
    water = db.Column(db.Integer,nullable= False)
    date = db.Column(db.BigInteger,nullable= False)
    time = db.Column(db.BigInteger,nullable=False)

    def __init__(self, UserId, actionId, actionCate, number, standnumber, energy, water, date, time):
        self.UserId = UserId
        self.actionId = actionId
        self.actionCate = actionCate
        self.number = number
        self.standnumber = standnumber
        self.energy = energy
        self.water = water
        self.date = date
        self.time = time

    def __lt__(self, other):
        return self.date < other.date

class Bodydata(db.Model):
    __tablename__ = 'bodydata'

    UserId = db.Column(db.Integer,primary_key=True, nullable=False,)
    height = db.Column(db.Integer, nullable=False)
    weight = db.Column(db.Integer, nullable=False)
    sex = db.Column(db.Integer, nullable=False)
    age = db.Column(db.Integer, nullable=False)

    def __init__(self, UserId, height, weight, sex, age):
        self.UserId = UserId
        self.height = height
        self.weight = weight
        self.sex = sex
        self.age = age

#新增用户
def insertUser(UserId, UserName, Password):
    user = User(UserId, UserName, Password)
    db.session.add(user)
    db.session.commit()
#用于注册以及登录使用
def queryUser(UserName):
    user = User.query.filter_by(UserName = UserName).first()
    return user

#新增身体数据
def insertBodyData( UserId, height, weight, sex, age):
    bodydata = Bodydata( UserId, height, weight, sex, age)
    db.session.add(bodydata)
    db.session.commit()
#身体数据设置界面，显示数据
def queryBodyData(UserId):
    bodydata = Bodydata.query.filter_by(UserId = UserId).first()
    return bodydata
#更新身体数据
def updateBodyData( UserId, height, weight, sex, age):
    bodydata = Bodydata.query.filter_by(UserId=UserId).first()
    bodydata.height = height
    bodydata.weight = weight
    bodydata.sex = sex
    bodydata.age = age
    db.session.commit()

#增加新动作
def insertAction( UserId, actionId, actionCate, number, standnumber, energy, water, date, time):
    action = Action(UserId, actionId, actionCate, number, standnumber, energy, water, date, time)
    db.session.add(action)
    db.session.commit()
#查找最新的动作,使用时间进行排序，因为使用时间进行排序
def queryLatestAction(UserId):
    action = Action.query.filter_by(UserId = UserId).all()
    action.sort()
    return action[-1]
#查找该用户所有的动作
def queryAllAction(UserId):
    actionList = Action.query.filter_by(UserId = UserId).all()
    return actionList

