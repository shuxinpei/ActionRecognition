from flask import Flask
from flask.ext.sqlalchemy import SQLAlchemy
import config

databaseurl = 'mysql+pymysql://%s:%s@%s:%s/%s' % (
config.MYSQL_USER, config.MYSQL_PASS, config.MYSQL_HOST, config.MYSQL_PORT, config.MYSQL_DB)

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = databaseurl
app.config['SQLALCHEMY_COMMIT_ON_TEARDOWN'] = True
db = SQLAlchemy(app)

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

db.create_all()

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
#查找最新的动作
def queryLatestAction(UserId):
    action =  Action.query.filter_by(UserId=UserId).first()
    return action
#查找该用户所有的动作
def queryAllAction(UserId):
    actionList = Action.query.filter_by(UserId = UserId).all()
    return actionList

insertAction("123456789","189123","2","10","8","12345","123","201878","20")
insertAction("123456789","111111111","2","10","8","11111","11111","201111878","20")
action = queryLatestAction("123456789")
print(str(action.UserId)+" "+str(action.number)+str(action.energy))
actions = queryAllAction("123456789")
for action in actions:
    print(str(action.UserId) + " " + str(action.number) + str(action.energy))
