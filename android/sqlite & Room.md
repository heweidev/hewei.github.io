# sqlite & Room
Room是对sqlite的封装，从而方便sqite的使用

1. index 索引，通过创建索引可以加快查询速度，但是同时减慢了插入和更新的速度
2. unique 可以制定某列的值不能重复
3. 数据库升级发生在首次访问数据库的时候getReadableDatabase或者getWritableDatabase被调用的时候

## Room中的索引
在表的Entity中声明
@Entity(indices = @Index{value = '', unique = true})