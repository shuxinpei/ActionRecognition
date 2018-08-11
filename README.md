# ActionRecognition姿态解算与动作识别
### Use 45 Feature to recognition the class of the action.
#### 使用sklearn库中进行动作的识别，现在只能做到二分类。<br><br>
  1.特征有45维度，数据处理函数已经封装，可以指定随机去除指定数量的特征<br><br>
  2.可以指定分类的类别，数据中有八个类别的动作，代码中使用的是对动作1和2进行分类<br><br>
  3.通过使用高斯核函数以及简单调参以后动作一二的识别准确率在82%左右波动<br><br><br>
  4.进行新动作的识别时需要重新调参
之后预期使用集成学习方式进行多类别的分类，欢迎pr
<br><br>
#### 同时配套有Android程序，定位为健身伴侣
用于自由运动时的动作识别与计数，能量消耗统计
以及特定动作的计数以及能量消耗预估
实现的功能有：
通过使用xgboost训练的模型进行自由运动中动作的识别
通过使用dtw以及特定动作的标准与否计算，以及计数和能量消耗预估
#

