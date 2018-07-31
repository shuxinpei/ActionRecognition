from flask import Flask
from flask.ext.sqlalchemy import SQLAlchemy
import config

databaseurl =  'mysql+pymysql://%s:%s@%s:%s/%s' % (config.MYSQL_USER, config.MYSQL_PASS, config.MYSQL_HOST, config.MYSQL_PORT, config.MYSQL_DB)
print(databaseurl)

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = databaseurl
app.config['SQLALCHEMY_COMMIT_ON_TEARDOWN'] = True
db = SQLAlchemy(app)

class user(db.Model):
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
class action(db.Model):
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

class bodydata(db.Model):
    __tablename__ = 'bodydata'

    UserId = db.Column(db.Integer, primary_key=True, nullable=False)
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


newaction = action(12345,15,5,12,10,452,153,12222121,122212)
newbodydata = bodydata(12345,182,140,1,20)
db.session.add(newaction)
db.session.add(newbodydata)
db.session.commit()
print("done")
