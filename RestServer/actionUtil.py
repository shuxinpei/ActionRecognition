import random
import math
from pandas.core.frame import DataFrame
import xgboost as xgb
import pandas as pd
from sklearn.preprocessing import LabelEncoder

'''
bst = xgb.Booster({'nthread':4}) #init model
bst.load_model("model.bin") # load data
model = xgboost.Booster.load_model("0001.model")
'''

def calActions(data):
    data = DataFrame(data)
    xg_test = xgb.DMatrix(data)

    clf = xgb.XGBClassifier()
    booster = xgb.Booster()
    booster.load_model('0001.model')
    clf._Booster = booster
    result = clf.predict(xg_test)

    return result

def calActions2(data,label):
    clf = xgb.XGBClassifier()
    booster = xgb.Booster()
    booster.load_model('0001.model')
    clf._Booster = booster
    clf._le = LabelEncoder().fit(label)
    result = clf.predict(data)

    return result

data = pd.read_csv('D:\ds2acc3.csv')
for i in range(len(data)):
        data.iloc[i,-1] -= 1
x = data.iloc[0:2684, :135]
# x = np.c_[data.iloc[0:2684, :45], data.iloc[0:2684, 90:135]]
y = data.iloc[0:2684, 136]

result = calActions2(x,y)
print(result[-100:])

class Action():

    UserId = 0
    actionId = random.randint(0,math.pow(10,16))
    actionCate = 0
    number =0
    standnumber = 0
    energy = 0
    water = 0
    date = 0
    time = 0

    def __init__(self, UserId, actionCate, number, standnumber, energy, water, date, time):
        self.UserId = UserId
        self.actionCate = actionCate
        self.number = number
        self.standnumber = standnumber
        self.energy = energy
        self.water = water
        self.date = date
        self.time = time

    def __lt__(self, other):
        return self.date < other.date