# LinearLayout

## Double taxation
     (水平的线性布局或者加入measureWithLargestChild的垂直布局)A LinearLayout view could result in a double layout-and-measure pass if you make it horizontal. A double layout-and-measure pass may also occur in a vertical orientation if you add measureWithLargestChild, in which case the framework may need to do a second pass to resolve the proper sizes of objects.

     