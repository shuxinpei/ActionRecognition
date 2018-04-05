
# coding: utf-8

# In[125]:


import numpy as np  
import scipy as sp  
from sklearn import svm  
from sklearn.cross_validation import train_test_split  
import matplotlib.pyplot as plt
import sklearn.externals  
from sklearn.model_selection import GridSearchCV
import random
#准备数据
def GetSimpleAndTarget( ClassOne, ClassTwo):
    data   = []  
    labels = []  
    a=0
    with open("C:/Users/Administrator/Desktop/DS1.csv") as ifile:  
            for line in ifile:  
                tokens = line.strip().split(',')
                if a!=0:
                    label = int(float(tokens[-1]))
                    if( label==int(ClassOne) or label==int(ClassTwo)): 
                        data.append([float(tk) for tk in tokens[:-2]])  
                        labels.append(int(float(tokens[-1])))
                else:
                    a=a+1
    x = np.array(data)  
    labels = np.array(labels)
    labels.reshape(683,1)
    return x,labels
#data(683,45),labels(683,1)


# In[126]:


#随机取15个特征进行去除，返回一个新的（n,30）矩阵
def ChangeDimension(data,count):
    new_data=data
    while(count>0):
        index = random.randint(0,(44-15+count))
        new_data = np.delete(new_data,index,axis=1)
        count-=1
    return new_data


# In[127]:


'''
#使用SVM进行训练
clf = svm.SVC(kernel="rbf",gamma=0.00119155,probability=1)   
clf.fit(data[:500], labels[:500])
#得到预测结果
result = clf.predict(data[-180:])
print(result)
'''
#训练函数
def trainData(data, labels):
    clf = svm.SVC()
    grid = GridSearchCV(clf,param_grid={"C":[0.37,0.3705,0.36995,0.37001], "gamma": [0.000000000051,0.00000000005,0.00000000049,0.00000000001]}, cv=4)
    grid.fit(data, labels)
    print("The best parameters are %s with a score of %0.2f"% (grid.best_params_, grid.best_score_))
    sklearn.externals.joblib.dump(grid, "C:/Users/Administrator/Desktop/train_model.m")


# In[128]:


#准备数据，目标类别 1 2 ，
data,labels = GetSimpleAndTarget(1,2)
#随机去除15个特征
data =ChangeDimension(data,15)
#进行训练，并保存模型
trainData(data, labels)


# In[129]:


#调用模型
clf_new = sklearn.externals.joblib.load("C:/Users/Administrator/Desktop/train_model.m")


# In[130]:


#使用上面的数据进行验证，查看有没有用
clf_new.predict(data[-180:])

