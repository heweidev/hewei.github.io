# ConstaintLayout

将view树平面话，提升view效率
替代RelativeLayout的绝佳选择

> To define a view's position in ConstraintLayout, you must add at least one horizontal and one vertical constraint for the view. Each constraint represents a connection or alignment to another view, the parent layout, or an invisible guideline. Each constraint defines the view's position along either the vertical or horizontal axis; so each view must have a minimum of one constraint for each axis, but often more are necessary.

每个子View在水平和垂直方向至少有一个约束（Constaint）,
约束表示view跟其他view的connection或者alignment

锚点anchor 
GuideLine 水平或者垂直方向的辅助线

Barrier 从单个或者多个view导出一个边界，这个边界可以用作其他view的锚点
Barrier也可以包含guideline，从而限制Barrier的最小和最大尺寸

