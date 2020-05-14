Mysql调优



# 一、索引优化分布

sql解析顺序

FROM--ON--JOIN--WHERE--GROUP BY--HAVING -- SELECT -- ORDER BY -- LIMIT



# SQL中的七种JOIN

INNER JOIN:取两表相交  

```mysql
SELECT <select_list>
FROM Table A
INNER JOIN Table B
ON A.key = B.key
```



LEFT JOIN：取左表全部

```mysql
SELECT <select_list>
FROM Table A
LEFT JOIN Table B
ON A.key = B.key
```



RIGHT JOIN：取右表全部

```mysql
SELECT <select_list>
FROM Table A
RIGHT JOIN Table B
ON A.key = B.key
```



LEFT JOIN (只取不相交的左表)

```mysql
SELECT <select_list>
FROM Table A
LEFT JOIN Table B
ON A.key = B.key
WHERE B.Key is NULL
```



RIGHT JOIN (只取不相交的右表)

```mysql
SELECT <select_list>
FROM Table A
RIGHT JOIN Table B
ON A.key = B.key
WHERE B.Key is NULL
```



(ORCAL独有，MYSQL不行)

FULL OUTER JOIN: 全连接,取两表全部数据

```mysql
SELECT <select_list>
FROM Table A
FULL OUTER JOIN Table B
ON A.key = B.key
```



FULL OUTER JOIN: 全连接,取两表不相交的数据

```mysql
SELECT <select_list>
FROM Table A
FULL OUTER JOIN Table B
ON A.key = B.key
WHERE A.key is NULL 
OR B.key is NULL
```

 

union(合并加去重)



# 索引简介

## 		是什么

索引(Index)是帮助MySQL高效过去数据的数据结构，索引是数据结构。

目的：在于提高查询效率，可以类比字典。

可以理解为“**排好序的快速查找数据结构。**”——数据本身之外，数据库还维护一个满足特定查找算法的数据结构，让这些结构以某种方式指向数据（一般为B-Tree），就可以在这些数据结构的基础上实现高级查找算法,这个数据结构就是索引。

一般索引本身也很大，不可能全部存储在内存 中，因此索引往往以索引文件的方式存储在磁盘上。

如果没有特别指明，都是指B-Tree(多路搜索树，并不一定是二叉树)结构 组织的索引。其中聚集索引，次要索引，覆盖索引，符合索引，前缀索引，唯一索引默认都是使用B+树索引，统称索引。除了B+树还有哈希索引（hash index）等



## 	优势

​	类似图书馆建书目索引，提高数据检索的效率，降低数据库的IO成本

​	通过索引列对数据进行排序，降低数据排序的成本，降低了CPU的消耗



## 	劣势

​	索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录，所以索引列也是占用空间的

​	虽然大大提高了查询速度，同时会降低更新表的速度，如对表进行INSERT、UPDATE和DELETE。因为更新表是，MySQL不仅要保存数据，还要保存一下索引文件每次更新添加索引列的字段，都会调整因为更新所带来的键值变化的索引信息。

​	索引只是提高效率的一个因素，如果Mysql有大数据量的表，就需要花时间研究建立最优秀的索引，或优化查询sql



## 索引分类



### 	单值索引

​			一个索引只包含单个列，一个表可以有多个单列索引



### 	唯一索引

​			索引列的值必须唯一，但允许有空值



### 	复合索引

​			一个索引包含多个列

###  

### 	基本语法	

创建：CREATE  [UNIQUE] INDEX indexName ON mytable(columnname(length));

​			ALTER mytable ADD [UNIQUE] INDEX [indexName] ON (columnname(length))

删除：DROP INDEX [indexName] ON mytable;

查看：SHOW INDEX FROM TABLE_name\G



## 	mysql索引结构

### 		BTree索引

### 		Hash索引

### 		full-text全文索引

### 		R-Tree索引



## 那些情况需要创建索引

​		1.主键自动创建唯一索引

​		2.频繁作为查询条件的字段应该创建索引

​		3.查询中与其他表关联的字段，外键关系建立索引

​		4.频繁更新的字段不适合创建索引 ——因为每次更新不单单是更新了记录还会更新索引

​		5.where 条件里用不到的字段不创建索引（where的字段）

​		6.单键/组合索引的选择问题，who？（在高并发下倾向创建组合索引）

​		7.查询中排序的字段，排序字段若通过索引去访问将大大提高排序速度（order by 的字段）

​		8.查询中统计或者分组的字段（group的字段）



## 那些情况不需要创建索引

​		1.表记录太少（300万已经很慢）

​		2.经常增删改的表——WHY:提高了查询速度，同时却会降低更新表的速度

​		3.数据重复且分布平均的表字段，因此应该只为最经常查询和最经常排序的数据列建立索引（**注意，如果某个数据列包含许多重复的内容，为它建立所以就没有太大的实际效果**）



# 性能分析

## 		MySql Query Optimizer(Mysql自带的优化分析器)

​				1.专门负责优化SELECT语句的优化器模块，主要功能：通过计算分析系统中收集到的统计信息，为客户端请求的Query提供它人文最优的执行计划（但不见得是DBA人为是最优的。）

​				2.当客户端向Mysql请求一条Query，命令解析器模块完成请求分类，区别出是SELECT并转发给MySql Query Optimizer，它会首先对整条Query进行优化，处理掉一些常量表达式的预算，直接换算成常量值，并对Query中的查询条件进行简化和转换，如去掉一些无用或显而易见的条件、结构调整等。然后分析Query中的Hint信息（如果有），看现实Hint信息是否可以完全确定该Query的执行计划，如果没有Hint或Hint信息还不足以完全确定执行计划，则会读取所涉及对象的统计信息，根据Query进行写相应的计算分析，然后再得到最后的执行计划。



## 		MySQL常见瓶颈

​			CPU：CPU在饱和的时候一般发生在数据装入内存或从磁盘上读取数据时候

​			IO：磁盘I/O瓶颈发生在装入数据远大于内存容量的时候

​			（Linux）服务器硬件的性能瓶颈：top、free、iostat和vmstat来查看系统的性能状态



## 		Explain(执行计划)

### 			是什么

​					EXPLAIN关键字可以模拟优化器执行SQL查询语句，从而知道MySql是如何处理你的SQL语句的。分析你的查询语句伙食表结构的性能瓶颈

### 			

### 			能干嘛

​					 表的读取顺序

​					 数据读取操作的操作类型

​					哪些索引可以使用

​					哪些索引被实际使用

​					表之间的引用

​					每张表有多少行被优化器查询	

### 			

### 			怎么玩

​						explain + sql语句

​						执行计划包含的信息（id | select_type | table | type | possible_keys | key | key_len |ref |rows | Extra）

### 			各字段解析



#### *id*：查询的序列号，主要用于表示执行顺序

​								select查询的序列号，包含一组数字，表示查询中执行select子句或操作表的顺序

​								***三种情况***

​									id相同，执行顺序由上至下

​									id不同，如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行

​									id相同不同，同时存在（结合上面两张情况，id大先执行，id相同顺序执行）



#### select_type*：查询类型，主要用于区别普通查询，联合查询，子查询等复杂查询

​									**SIMPLE**：简单查询，查询中不包含子查询或UNION

​									**PRIMARY**：主查询,查询中包含复杂的子部分，最外层查询被标记为

​									**SUBQUERY**：在SELECT或WHERE列表中包含了子查询

​									**DERIVED**：在From列表中包含的子查询被标记为DERIVED（衍生）Mysql会递归执行这些子查询，把结果放在临时表（会增加负担）里。

​									**UNION**：若第二个SELECT出现在UNION之后，则被标记为UNION;若UNION包含在FROM字句的子查询中，外层SELECT将被标记为：DERIVED

​									**UNION RESULT**：从UNION表获取结果的SELECT



#### *table：slelect*中用到的表，Derived是衍生表不是真的表

#### *type*：访问类型排列

​									ALL、index、range、ref、eq_ref、const，system、NULL

​									**从最好到最坏**

​									*system>const>eq_ref>ref>fulltext>ref_or_null>index_merge>unique_subquery*

>*index_subquery>rangeindex>ALL*									

​									常见的 ：*system>const>eq_ref>ref>range>index>ALL*

​									一般来说，得保证查询至少是range、ref

------------------------------------------------------------------------------------------------

***systm***：表只有一行记录（等于系统表）,这是const类型的特列，平时不会出现，这个可以忽略不计

 ***const***：表示通过索引一次就找到了，const用于比较primary key或者unique索引，因为只匹配一行数据，索引很快。如将主键置于where列表中，Mysql就能将该查询转换为一个常量

***eq_ref：***唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配，常见于主键或唯一索引扫描

 ref***：非唯一索引扫描，返回匹配某个单独值的所有行。本质上也是一种索引访问，它返回所有匹配某个单独值的行，然而它可能会找到多个符合条件的行，所以它应该属于查找和扫描的混合体。

***range***：只检索给定防伪的行，使用一个索引来选择行，key列显示使用了那个索引。一般就是在你的where语句中出现between，<,>,in等查询。这种范围扫描索引比全表扫描要好，因为它只需要开始于索引的某一点，而结束语另一点，不用扫描全部索引。

***index***（全索引扫描）：Full Index Scan,与ALL区别为index类型只遍历索引树，这通常比ALL快，因为索引文件通常比数据文件小，（虽然all和Index都是读全表，但index是从索引中读取的，而all是从硬盘中读的。）

***all*** : 全表扫描

****



#### *possible_keys*：可能用到的key

显示可能应用在这张表中的索引，一个或多个

查询涉及的字段上若存在索引，则该索引将被列出，**但不一定被查询实际使用**		



#### *key*：实际用到的key

实际使用的索引，如果为NULL,则没有使用索引

查询中若使用了覆盖索引，则该索引仅出现在key列表中



#### *key_len*：索引中使用的字节数

表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度，在***不损失精确性的情况下，长度越短越好***

key_len显示的值为索引字段的最大可能长度，***并非实际使用长度，***即key_len是根据表定义计算而得，不是通过表内检索出的



#### *ref*：被用于索引列上的列或常数

显示索引的哪一列被使用了，如果可能的话，是一个常数，哪些列或常量被用于查找索引列上的值



#### *rows*：**每个查询有多少行被扫描就找到结果**

根据表统计信息及索引选用情况，大致估算出找到所需的记录所需要读取的行数（**每个查询有多少行被扫描就找到结果**）



#### *Extra*：包含不适合在其他列显示但十分重要的额外信息

*1.**Using filesort*** （**出现需要尽快优化**）:说明mysql会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。MySQL中***无法利用索引完成的排序操作成为“文件排序”***。**修改order by ,group by的参数**……



2.***Using temporary***（***临时表创建很消耗空间时间***，***需要立刻优化***） :使用了临时表保存中间结果，MySQL在对查询结果排序是使用临时表。常见于排序order by和分组group by



3.***Using index*** （***效率不错！***）：表示相应的select操作中使用了覆盖索引（Covering Index），避免访问了表的数据行，效率不错！

如果同时出现using where，表明索引被用来执行索引键值的查找；

如果没有同时出现using where，表明索引用来读取数据而非执行查找操作

***覆盖索引（Covering Index）***：就是select的数据列只用从索引中就能取得，不必读取数据行，MySQL可以利用索引返回select列表中的字段，而不必根据索引再次读取数据文件，换句话说***查询列要被所建的索引覆盖。***

如果使用覆盖索引，一定要注意select 列表中只取出需要的列（在索引中的），不可select *



4.*Using where*：使用了where

5.*Using join buffer*：使用了连接缓存

6.*impossible where* :  where子句的值总是false，不能用来获取任何元组

7.*select tables optimized away* :  在没有group by 子句的情况下，基于索引优化MIN/MAX操作或者对于MyISAM存储引擎优化Count(*)操作，不必等到执行阶段再进行计算，查询执行计划生产的阶段即完成优化。

8.*distinct*:  优化distinct操作，在找到第一匹配的元组后即停止找同样值的动作



# 索引优化



## 索引分析

​		**单表分析**

​		<、>是范围搜索，会引起索引失效 ;=是常量，可以使用索引。

​		索引的值不包括范围搜索，即可引用索引



​		**两表分析**（left/right/inner  join）

​		 左右连接，索引相反加。原理：左外链接是左表全表搜索，索引优化不大，右边加索引有优化。



​		**三表分析**

​		参考两表。后两个都要加索引

​		

​		尽可能减少Join语句中的NestedLoop的循环总次数：“永远用小结果集驱动大的结果集”

​		优先优化NestedLoop的内层循环：

​		保证Join语句中被驱动表上Join条件字段已经被索引：	

​		当无法保证被驱动表的Join条件字段被索引且内存资源充足的前提下，不要太吝惜JoinBuffer的设置：



## 	索引失效（应该避免）

### 		常见原因：

**1.全值匹配**（最优，不用优化）

​			

**2.最佳左前缀法则**

​		如果是复杂索引，要遵守*最左前缀法则*，指的是查询***从索引的最左前列开始***并且***不跳过索引中的列。***

​		跳过了，会出现断档的情况，后面的索引不起效。

​	  （***带头大哥不能死，中间兄弟不能少***）

​		5.6之后可以使用索引了，不过key_len长度很大。



**3.不在索引列上做任何操作（计算、函数、（自动 or 手动）类型转换，会导致索引失效而转向全表扫描）**



**4.存储引擎不能使用索引中范围条件（<、>）右边的列**

​	a = xx and b > 0 and c=xx	

​	范围之后全失效(索引c不生效)。5.6之后可以使用索引了，不过key_len长度很大。



**5.尽量使用覆盖索引（只访问索引的查询（索引列和查询列一致）），减少select  * 操作**



**6.mysql在使用不等于（！=）的时候无法使用索引会导致全表扫描**

5.6之前无法使用索引，5.6之后可以使用索引了，不过key_len长度很大。



**7.is null，is not null也无法使用索引**

5.6之前无法使用索引，5.6之后可以使用索引了，不过key_len长度很大。



### **8.like以通配符开头（‘%abc...’）mysql索引失效会变成全表扫描的操作**

范围之后全失效

可以用覆盖索引（select 索引列）解决，5.6之后好像只是变成range，而不是all



**9.字符串不加单引号索引失效**

5.6之后可以使用索引了，不过key_len长度很大。



**10.少用or，用它来连接是索引会失效**

5.6之后可以使用索引了，不过key_len长度很大。





***在索引分析的时候定值、范围还是排序，一般 order by 是给个范围。group by基本上都需要进行排序，会有临时表产生。***



## 一般性建议

对于单键索引，尽量选择针对当前query过滤性更好的索引

在选择组合索引的时候，当前Query中过滤性最好的字段在索引字段顺序中，位置越靠前越好

在选择组合索引的时候，尽量选择能够包含当前query和where字句中更多字段的索引

尽可能通过分析统计信息和调整query的写法来达到悬着合适索引的目的





# 3.查询截取分析

1.慢查询的开启并捕获（至少跑1天，看看生产的慢sql情况，开启慢查询日志，设置阈值。比如超过5秒就是慢sql，抓取出来）

2.explain+慢sql分析

3.show profile 查询SQL在mysql服务器里面的执行细节和生命周期情况

4.SQL数据库服务器的参数调优





# 查询优化

## 	优化原则

**小表驱动大表**（小的数据集驱动大的数据集）

当B表的数据集必须小于A表的数据集时，用in优于exists。

当A表的数据集必须小于B表的数据集时，用exists优于in。

exists:（子查询返回常数n）将住查询的数据，放到子查询中做条件验证，根据验证结果（TRUE或FALSE），决定主查询的数据结果是否得以保留。

in：子查询返回数据集



## 排序优化（ORDER BY）

### 	Order by子句，尽量用Index方式排序，避免使用FileSort方式排序

SQL支持二种方式排序，FileSort和Index，Index效率高

它指MySQL扫描索引本身完成排序，FileSort方式效率低

**Order by 满足两情况，会使用Index方式排序：**

Order by语句使用索引最左前列；

使用where子句和Order by子句条件列组合满足索引最左前列

升序，降序要一致

-------



### 尽可能在索引列上完成排序操作，遵照索引建的最佳左前缀



### 如果不在索引列上，filesort有两种算法。mysql就要启动以双路排序和单路排序

双路排序：Mysql4.1之前是使用双路排序，扫描两次磁盘，最终得到数据，读取行指针和order by列，对他们进行排序，然后扫描已经排序好的列表，按照列表中的值重新从列表中读取对应的数据

从磁盘取排序字段，在buffer进行排序，再从磁盘取其他字段



取一批数据，要对磁盘进行两次扫描，I/O是很耗时的，所以在mysql4.1之后，出现了第二种改进的算法，单路排序

单路排序：从磁盘读取查询需要的所有列，按照order by 列在buffer对它们进行排序，然后扫描排序后的列表进行输出，它的效率更快一些，避免了第二次读取数据，并且把随机IO变成了顺序IO,但是它会使用更多的空间，因为它把每一行都保存在了内存中了。



结论：**单路好过双路，但是单路存在问题**

在sort_buffer中，方法B比A要多占用很多空间，因为方法B是把所有字段都去除，所以有可能去除的数据总大小超出了sort_buffer的容量，导致每次只能取sort_buffer容量大小的数据，进行排序（创建tmp文件，多路合并），排完再取sort_buffer容量大小，再排……从而多次I/O

本来想省一次I/O操作，反而导致了大量的I/O操作，反而得不偿失。



***优化策略***

增大sort_buffer_size参数的设置

增大max_length_for_sort_data参数的设置

***原因***

提高Order By的速度

**1.**Order by时select *是一个大忌，只Query需要的字段，这点非常重要，在这里影响的是：

​	1.1 当**Query**的字段带下总和小于**max_length_for_sort_data**而且排序字段不是**TEXT|BLOB**类型时，会用单路排序，否则用多路排序

​	1.2 两种算法的数据都有可能超出sort_buffer的容量，超出之后，会创建tmp文件进行合并排序，导致多次I/O，但是用单路排序的风险会更大一些，所以要提高sort_buffer_size



**2.**尝试提高sort_buffer_size

​	不管哪种算法，提高这个参数都会提高效率，当然，要更加系统的能力去提到，因为这个参数是针对每个进程的



**3.**尝试提高max_length_for_sort_data

提高这个参数，会增加用单路算法的概率，但是如果设的太高，数据总容量超出sort_buffer_size的概率就增大，明显症状是高的磁盘I/O活动和低的处理器使用率。

------



## GROUP BY关键字优化

### 1.group by 实质是先排序后进行分组，遵照索引建的最佳左前缀

### 2.当无法使用索引列，增大max_length_for_sort_data参数的设置 + 增大sort_buffer_size参数的设置

### 3.where 高于 having ， 能写在where限定的条件就不用去having限定了





# 慢SQL日志查询

默认下，MySQL数据库没有开启慢查询日志，需要手动设置参数

如果不是调优需要的话，一般不建议启动该参数，因为开启慢查询日志或多或少带来一定的性能影响。慢查询日志支持将日志记录写入文件。

### 开启慢查询日志

**SHOW VARIABLES LIKE %slow_query_log%;**

**set global slow_query_log=1;**  (只对本数据库起效，重启后失效)

要永久生效就有修改配置文件 my.ini/my.cnf

[mysqld]下增加或修改参数,再重启mysql

slow_query_log=1

slow_query_log_file=/var/lib/mysql/slow-sql.log



### 查看阈值

SHOW VARIABLES LIKE 'long_query_time%';

设置阈值,修改完之后需要重新连接或新开一个会话才能看到修改值

set global long_query_time=3;



模拟线程睡眠，可以测试作为慢sql写入日记

select sleep(4);

### 查看当前系统有多少条慢查询

show global status like '%Slow_queries%';

[mysqld]下的配置：

long_query_time=3;

log_output=FILE



## 日志分析工具mysqldumpslow

帮助信息

s:是表示按照何种方式排序

c：访问次数

l:锁定时间

r:返回记录

t:查询时间

al：平均锁定时间

at：平均查询时间

t：即为返回前面多少条的数据

g：后边搭配一个正则匹配模式，大小写不敏感的



### 常用句式

得到返回记录集最多的10个sql

mysqldumpslow -s r -t 10 日记url



得到访问次数最多的10个sql

mysqldumpslow -s c -t 10 日记url



得到按照时间排序的前10条里面含有左连接的查询语句

mysqldumpslow -s t -t 10 -g "left join"  日记url



建议在使用这些命令时结合 | 和 more 使用，否则有可能出现爆屏情况

mysqldumpslow -s r -t 10 日记URL |more



# 批量数据脚本

函数有返回值

存储过程没有返回值



创建函数，假如报错：This function has none of DETERMINISTIC……

#由于开启过慢查询日志，因为我们开启了bin-log，我们必须为我们的function指定一个参数

## 设置参数log_bin_trust_function_creators

show variables like 'log_bin_trust_function_creators';

set global log_bin_trust_function_creators=1;

#[mysqld]配置 my.ini/my.cnf

log_bin_trust_function_creators=1



## 创建函数，保证每条数据都不同

**随机产生字符串**

DELIMITER $$

CREATE FUNCTION rand_string(n INT) RETURNS VARCHAR(255)

BEGIN

​	DECLARE chars_str VARCHAR(100) DEFAULT 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';

​	DECLARE return_str VARCHAR(100) DEFAULT '';

​	DECLARE i INT DEFAULT 0;

​	WHILE i < n DO

​	SET return_str = CONCAT(return_str,SUBSTRING(chars_str,FLOOR(1+RAND()*52),1));

​	SET i = i + 1;

​	END WHILE;

​	RETRUN return_str;

END $$



**随机产生部门编号**

DELIMITER $$

CREATE FUNCTION rand_NUM(n INT) RETURNS INT(5)

BEGIN

​	DECLARE i INT DEFAULT 0;

​	SET i = FLOOR(100+RAND()*10);

​	RETRUN i;

END $$



## 创建存储过程

创建往emp插入数据的存储过程

DELIMITER $$

CREATE PROCEDURE insert_emp(IN START  INT(10),IN max_num INT(10))

BEGIN

​	SET autocommit= 0; #自动提交设为0，最后一起提交

​	DECLARE i INT DEFAULT 0;

​	REPEAT

​	SET i = i + 1;

​	INSERT INTO emp(empno,ename,job,mgr,hiredate,sal,comm,deptno) VALUES ((START+i),rand_string(6),'SALESMAN',00001,CURDATE(),2000,400,rand_num());

​	UNTIL i = max_num

​	END REPEAT;

COMMIT;

END $$



## 调用存储过程

DELIMITER;

CALL insert_dept(100,10);



# Show Profile

是mysql提供可以用来分析当前绘画中语句执行的资源消耗情况，可以用于SQL的调优的测量

官网：http://dev.mysql.com/doc/refman/5.5/en/show-profile.html

默认情况下，参数处于关闭状态，并保存最近15次的运行结果



## 	分析步骤

​		**1.**是否支持，看看当前的mysql版本是否支持

​			Show variables like 'profiling'

​		**2.**开启功能，默认关闭

​			set profiling = on;

​		**3.**运行SQL

​		**4.**查看结果，show profiles;

​			Query_ID  | Duration  | Query

 

​		**5.**诊断SQL,show profile cpu, block io for query 

​			**show profile cpu,block io for query**  Query_ID;

​			Status | Duration | CPU_user | CPU_system | Block_ops_in| Block_ops_out



​		**6.**日常开发需要注意的结论

​			converting HEAP to MyISAM 查询结果太大，内存都不够用了，往磁盘上搬了

​			Creating tmp table 创建临时表  拷贝数据到临时表，用完再删除

​			Copying to tmp table ondisk 把内存中临时表复制到磁盘，危险！！！

​			locked







# 全局查询日志