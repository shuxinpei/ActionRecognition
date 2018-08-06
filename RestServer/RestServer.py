from flask import Flask,request
from flask.ext.restful import Api, Resource
from flask.ext.restful import reqparse
import random
import math
import json
import pymysql
from DBUtil import User, Action, Bodydata
import DBUtil
from actionUtil import calActions
import config
from config import db

app = Flask(__name__)
databaseurl = 'mysql+pymysql://%s:%s@%s:%s/%s' % (config.MYSQL_USER, config.MYSQL_PASS, config.MYSQL_HOST, config.MYSQL_PORT, config.MYSQL_DB)
app.config['SQLALCHEMY_DATABASE_URI'] = databaseurl
app.config['SQLALCHEMY_COMMIT_ON_TEARDOWN'] = True
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db.init_app(app)
api = Api(app)

#用户注册api-----测试通过
class Regist(Resource):
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('username', type = str, required = True, help = 'No username', location = 'json')
        self.reqparse.add_argument('password', type = str, required = True, location = 'json')
        super(Regist, self).__init__()
    #get注册用户
    def get(self):
        username = request.args.get('username')
        password = request.args.get('password')
        #数据库查询是否拥有相同用户名的
        user = DBUtil.queryUser(username)
        if user:
            return {"msg":"error","userId":0}
        else:
            userId = random.randint(0, 2147483647)
            user = User(userId, username, password)
            #写入数据库
            DBUtil.insertUser(userId,username,password)
            #注册以后生成用户ID
            return {'msg': "success", "userId" :userId}

#用户登录api---------测试通过
class Login(Resource):
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('username', type = str, required = True, help = 'No username', location = 'json')
        self.reqparse.add_argument('password', type = str, required = True, location = 'json')
        super(Login, self).__init__()

    def get(self):
        username = request.args.get('username')
        password = request.args.get('password')
        print("name"+username+"//password"+password)
        print(type(username))
        user = DBUtil.queryUser(username)
        if user.Password == password:
            return {"msg":"success", "userId" :user.UserId}
        else:
            return {"msg":"error", "userId":0}

#特定动作,从客户端传输过来的数据需要是：用户ID，动作类别，运动时长,以及运动数据
#运动数据使用@来分割一个时间段，使用，分割每一个传感器的数据
#自由运动时候使用的，用于解算
class Action(Resource):
    def __init__(self):
        self.reqparse = reqparse.RequestParser()

        self.reqparse.add_argument('userId', type = int, required = True, location = 'json')
        self.reqparse.add_argument('date', type=int, required=True, location='json')
        self.reqparse.add_argument('time', type=int, required=True, location='json')
        #动作数据
        self.reqparse.add_argument('data', type=str, required=False, location='json')

        super(Action, self).__init__()

    def get(self):
        userId= request.args.get('userId')
        time = request.args.get('time')
        date = request.args.get("date")

        actiondata = request.args.get('data')
        #先为动作生成ID，自由运动与特定动作分开处理
        actionId = random.randint(0, 2147483647)
        #将数据存在dataArray中
        if actiondata:# 自由运动
            dataArray = []
            list = actiondata.split("@");
            for str in list:
                chlist = str.split(",")
                dataArray.append(chlist)
            for data in dataArray:
                print(data)
            #调用封装好的进行动作计算,返回一个动作的列表,将除了动作的类别，时间，能量，水分以外的数据都进行设置
            actionList = calActions(dataArray)
            resultJson = ""
            for actionRes in actionList:
                resultJson+= "@"+userId+"#"+str(actionRes.actionId)+"#"+str(actionRes.actionCate)+"#"+str(actionRes.number)+"#"+str(actionRes.standnumber)+"#"+str(actionRes.energy)+"#"+str(actionRes.water)+"#"+str(actionRes.data)+"#"+str(actionRes.time)
            return {"msg":"success","result":resultJson}
        else:
            return {"msg":"error"}


class SpecifyAction(Resource):
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('userId', type = int, required = True, location = 'json')
        self.reqparse.add_argument('actionCate', type = int, required = True, location = 'json')
        self.reqparse.add_argument('number', type = int, required = True, location = 'json')
        self.reqparse.add_argument('standnumber', type = int, required = True, location = 'json')
        self.reqparse.add_argument('energy', type = int, required = True, location = 'json')
        self.reqparse.add_argument('water', type = int, required = True, location = 'json')
        self.reqparse.add_argument('date', type=int, required=True, location='json')
        self.reqparse.add_argument('time', type=int, required=True, location='json')

        super(SpecifyAction, self).__init__()

    def get(self):
        userId = request.args.get('userId')
        actionCate = request.args.get('actionCate')
        number = request.args.get('number')
        standnumber = request.args.get('standnumber')
        energy = request.args.get('energy')
        water = request.args.get('water')
        date = request.args.get("date")
        time = request.args.get("time")
        #生成动作ID
        #保存在数据库中
        actionId = random.randint(0,2147483647)
        if (actionCate == "1" | "2" | "3" | "4" | "5" | "6"):
            action = Action(userId, actionId, actionCate, number, standnumber, energy, water,date, time)
            return {"msg": "success", "actionId": actionId}
        else:
            # 出错
            return {"msg": "error", "actionId": 00000}

api.add_resource(Login, '/login')
api.add_resource(Regist,"/regist")
api.add_resource(Action,'/action')
api.add_resource(SpecifyAction,'/SpecifyAction')

if __name__ == '__main__':
    app.run(debug=True)

