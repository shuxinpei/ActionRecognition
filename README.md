# ActionRecognition姿态解算与动作识别
<h3>Use 45 Feature to recognition the class of the action.<h3>
<h4>使用sklearn库中进行动作的识别，现在只能做到二分类。<h4><br>
  1.特征有45维度，数据处理函数可以指定随机去除指定数据量的特征<br><br>
  2.可以指定分类的类别，数据中有八个类别的动作，代码中使用的是对动作1和2进行分类<br><br>
  3.通过使用高斯核函数以及简单调参以后动作一二的识别准确率在82%左右波动<br><br><br>
之后预期使用集成学习方式进行多类别的分类，欢迎pr
