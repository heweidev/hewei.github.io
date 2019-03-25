## CDAP
- 注意输入和输出的数据类型以及是否非空。（CDAP不会自动转换）
- Date类型输入到CDAP中是long
- select中有where的时候要写成字表的形式 select * from (select * from xxx where xx = 11);
- 数据库增加字段要更新schema


- 导入excel时，如果某个单元格是空的，input数据是字符串'NULL'

