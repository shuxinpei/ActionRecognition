from flask import Flask,request
from flask.ext.restful import Api, Resource
from flask.ext.restful import reqparse
import random
import math
import json
import pymysql

app = Flask(__name__)
api = Api(app)

class Login(Resource):
    def get(self):
        return {'hello': 'world'}

#用户注册api
class Regist(Resource):
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('username', type = str, required = True, help = 'No username', location = 'json')
        self.reqparse.add_argument('password', type = str, required = True, location = 'json')
        super(Regist, self).__init__()

    def get(self):
        username = request.args.get('username')
        password = request.args.get('password')
        #数据库的用户ID
        #注册以后生成用户ID
        userId = random.randint(0,math.pow(10,16))
        return {'username': username, "password" : password ,"userId" : userId }

#用户登录api
class Login(Resource):
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('username', type = str, required = True, help = 'No username', location = 'json')
        self.reqparse.add_argument('password', type = str, required = True, location = 'json')
        super(Login, self).__init__()

    def get(self):
        username = request.args.get('username')
        password = request.args.get('password')
        userId = 123456 #这个要在数据库里面得到
        return {'username': username, "password" : password ,"userId" : userId}

#特定动作,从客户端传输过来的数据需要是：用户ID，动作类别，运动时长,以及运动数据
#运动数据使用@来分割一个时间段，使用，分割每一个传感器的数据
#自由运动时候使用的，用于解算
class Action(Resource):
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('userId', type = str, required = True, location = 'json')
        self.reqparse.add_argument('time', type = str, required = True, location = 'json')
        self.reqparse.add_argument('actionCate', type = str, required = True, location = 'json')
        self.reqparse.add_argument('actiondata', type = str, required = True, location = 'json')

        super(Action, self).__init__()

    def get(self):
        userId= request.args.get('userId')
        time = request.args.get('time')
        actionCate = request.args.get('actionCate')
        actiondata = request.args.get('actiondata')
        #将数据存在dataArray中
        dataArray = []
        list = actiondata.split("@");
        for str in list:
            chlist = str.split(",")
            dataArray.append(chlist)
        for data in dataArray:
            print(data)
        #调用执行脚本进行姿态解算以及能量消耗的预估

        #返回结果

        #保存数据

        return {"userId": userId, "time": time,"actionCate": actionCate, "actiondata": actiondata}

class SpecifyAction(Resource):
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('userId', type = str, required = True, location = 'json')
        self.reqparse.add_argument('actionCate', type = str, required = True, location = 'json')
        self.reqparse.add_argument('number', type = str, required = True, location = 'json')
        self.reqparse.add_argument('standnumber', type = str, required = True, location = 'json')
        self.reqparse.add_argument('energy', type = str, required = True, location = 'json')
        self.reqparse.add_argument('water', type = str, required = True, location = 'json')

        super(SpecifyAction, self).__init__()

    def get(self):
        userId = request.args.get('userId')
        actionCate = request.args.get('actionCate')
        number = request.args.get('number')
        standnumber = request.args.get('standnumber')
        energy = request.args.get('energy')
        water = request.args.get('water')
        #生成动作ID
        #保存在数据库中
        actionId = random.randint(0,math.pow(10,16))
        return {
                "userId": userId,
                "actionId" : actionId,
                "actionCate" : actionCate,
                "number" : number,
                "standnumber" : standnumber,
                "energy" : energy,
                "water" : water
        }

api.add_resource(Login, '/login')
api.add_resource(Regist,"/regist")
api.add_resource(Action,'/action')
api.add_resource(SpecifyAction,'/SpecifyAction')

if __name__ == '__main__':
    app.run(debug=True)

