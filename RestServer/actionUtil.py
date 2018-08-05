import random
import math
from pandas.core.frame import DataFrame
import xgboost as xgb
import pandas as pd
from sklearn.preprocessing import LabelEncoder
from DBUtil import Action
import time
import config

def calActions(data,label):
    #计算出动作
    clf = xgb.XGBClassifier()
    booster = xgb.Booster()
    booster.load_model('0001.model')
    clf._Booster = booster
    clf._le = LabelEncoder().fit(label)
    result = clf.predict(data)
    #将原始数据划分为一个一个的动作块
    actionBlocks = calActionStartAndEnd(data,result)
    #计算出次数，能量，标准以及水分的消耗
    calNumberEnergyWater(data, actionBlocks)
    #封装返回
    return calNumberEnergyWater(data, actionBlocks)

def calActionNum(data,result):
    return result

#通过动作识别的结果将动作划分
def calActionStartAndEnd(data,result):
    StartAndEnd = []
    start = 0
    for num,i in enumerate(result):
        if(num != result[start]):
            StartAndEnd.append(pair(start,i-1,data[start,i-1]),num)
            start = i
    return result
#计算消耗，返回一个动作的序列
def calNumberEnergyWater(data,actionBlocks):
    ActionList = []
    for pair in actionBlocks:
        #获取数据以及类别
        actionData = data[pair.start,pair.end]
        Cal = pair.cal
        number = calNumber(data,Cal)
        energy = calEnergy(data,Cal)
        standnum = calStandNumber(data,Cal)
        water = calWaterNumber(data,Cal)
        action = Action(Cal,number, standnum,energy,water,int(time.time()),len(actionData)*config.Circle)
        ActionList.append(action)
    return ActionList

def calNumber(data,cal):
    return 0
def calEnergy(data, cal):
    return 0
def calStandNumber(data, cal):
    return 0
def calWaterNumber(data,cal):
    return 0
'''
data = pd.read_csv('D:\ds2acc3.csv')
for i in range(len(data)):
        data.iloc[i,-1] -= 1
x = data.iloc[0:2684, :135]
# x = np.c_[data.iloc[0:2684, :45], data.iloc[0:2684, 90:135]]
y = data.iloc[0:2684, 136]
print(x[35:45])
print("****")
print(y[35:45])
result = calActions(x,y)
print(result[35:45])
'''

'''
存在问题：
    模型加载以后结果不对----------done 
    模型改成回归的
    数据格式是怎样的，舒雷师兄怎么的处理的
    使用回归以后写好遍历算法，返回最有可能的那个，之后返回
    返回以后封装成为动作，结合特征计算出动作的数据，例如能量，标准个数之类的
'''

#用于记录一个动作的开始以及结束
class pair():
    start = 0
    end = 0
    data = []
    cal = 0
    def __init__(self,start,end,data,cal):
        self.start = start
        self.end = end
        self.data = data
        self.cal = cal

class Action():
    actionId = random.randint(0,math.pow(10,16))
    actionCate = 0
    number =0
    standnumber = 0
    energy = 0
    water = 0
    date = 0
    time = 0

    def __init__(self ,actionCate, number, standnumber, energy, water, date, time):
        self.actionCate = actionCate
        self.number = number
        self.standnumber = standnumber
        self.energy = energy
        self.water = water
        self.date = date
        self.time = time

    def __lt__(self, other):
        return self.date < other.date