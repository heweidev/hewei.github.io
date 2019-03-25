# 泛型
类型仅存在于源码。在编译时，用Object和上下限替代泛型类型，然后加入强制类型转换。
## 类型擦出引起的两个问题
    1. 方法覆盖

        public class Pair<T> {
            T first;
            T second;

            void setSecond(T second) {
                this.second = second;
            }
        }

        public DataInterval extends Pari<Date> {
            void setSecond(Date second) {

            }
        }

        类型擦除后，与Pair类的setSecond方法冲突，不支持多态
        编译器自动插入setSecond（Object obj）方法

        DataInterval类变成如下：
          public DataInterval extends Pari {
            void setSecond(Date second) {

            }
        }

        对于get方法同理。在虚拟机中允许有仅有返回值不同的方法


        总结：
        1. 虚拟机中没有泛型
        2. 所有类型的参数都由它们的限定类型替换
        3. 桥接方法被生成来保护多态
        4. 为保护类型安全，适当时候加入类型转换

    2. 遗留代码的警告


## 约束与局限性
    1. 不能用基础类型实例化
    2. 运行时类型查询只适用于原始类型
    3. 不能创建实例化类型的数组
    4. 不能实例化类型变量 new T
    5. 不能构造泛型数组

