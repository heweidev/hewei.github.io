# sqlite & Room
Room是对sqlite的封装，从而方便sqite的使用

1. index 索引，通过创建索引可以加快查询速度，但是同时减慢了插入和更新的速度
2. unique 可以指定某列的值不能重复
3. 数据库升级发生在首次访问数据库的时候getReadableDatabase或者getWritableDatabase被调用的时候
4. replace语句会删除原有的一条记录， 并且插入一条新的记录来替换原记录。

## Room中的索引
在表的Entity中声明
@Entity(indices = @Index{value = '', unique = true})    


[sqlite replace](https://blog.csdn.net/zhangjg_blog/article/details/23267761)

总结如下：

- *update*
update语句使用where子句定位被更新的记录；
update语句可以一次更新一条记录， 也可以更新多条记录， 只要这多条记录都符合where子句的要求；
update只会在原记录上更新字段的值， 不会删除原有记录， 然后再插入新纪录；
如果在update语句中没有指定一些字段， 那么这些字段维持原有的值， 而不会被置空；

- *replace*
replace语句会删除原有的一条记录， 并且插入一条新的记录来替换原记录。
一般用replace语句替换一条记录的所有列， 如果在replace语句中没有指定某列， 在replace之后这列的值被置空 。 
replace根据表的唯一约束决定要替换哪条记录
replace语句不能根据where子句来定位要被替换的记录
如果执行replace语句时， 不存在要替换的记录， 那么就会插入一条新的记录。
如果replace的时候指定了主键，并且该值在表中已存在，会替换该记录。如果replace的某个unique字段跟表中另外一条记录相同，该记录将被删除