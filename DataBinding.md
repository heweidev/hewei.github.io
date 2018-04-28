# DataBinding

## simple data

<TextView android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@{user.lastName}"/>


## listener
    ### method
              <TextView android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="@{user.firstName}"
                    android:onClick="@{handlers::onClickFriend}"/>
    ## bind listener

        <layout xmlns:android="http://schemas.android.com/apk/res/android">
            <data>
                <variable name="task" type="com.android.example.Task" />
                <variable name="presenter" type="com.android.example.Presenter" />
            </data>
            <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent">
                <Button android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:onClick="@{() -> presenter.onSaveClick(task)}" />
            </LinearLayout>
        </layout>



## 有用的注解
    BindingConversion

    BindingAdapter

    InverterBindingAdapter


## 通过DataBinding给view增加额外属性
正常情况下view的属性不支持object类型。通过databinding可以给view增加额外的属性。

