### 

## Gson Goals
- Provide simple toJson() and fromJson() methods to convert Java objects to JSON and vice-versa
- Allow pre-existing unmodifiable objects to be converted to and from JSON
- Extensive support of Java Generics
- Allow custom representations for objects
- Support arbitrarily complex objects (with deep inheritance hierarchies and extensive use of generic types)

```
  public void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
    TypeAdapter<?> adapter = getAdapter(TypeToken.get(typeOfSrc));
    boolean oldLenient = writer.isLenient();
    writer.setLenient(true);
    boolean oldHtmlSafe = writer.isHtmlSafe();
    writer.setHtmlSafe(htmlSafe);
    boolean oldSerializeNulls = writer.getSerializeNulls();
    writer.setSerializeNulls(serializeNulls);
    try {
      ((TypeAdapter<Object>) adapter).write(writer, src);
    } catch (IOException e) {
      throw new JsonIOException(e);
    } finally {
      writer.setLenient(oldLenient);
      writer.setHtmlSafe(oldHtmlSafe);
      writer.setSerializeNulls(oldSerializeNulls);
    }
  }

    public <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
    TypeAdapter<?> cached = typeTokenCache.get(type == null ? NULL_KEY_SURROGATE : type);
    if (cached != null) {
      return (TypeAdapter<T>) cached;
    }

    Map<TypeToken<?>, FutureTypeAdapter<?>> threadCalls = calls.get();
    boolean requiresThreadLocalCleanup = false;
    if (threadCalls == null) {
      threadCalls = new HashMap<TypeToken<?>, FutureTypeAdapter<?>>();
      calls.set(threadCalls);
      requiresThreadLocalCleanup = true;
    }

    // the key and value type parameters always agree
    FutureTypeAdapter<T> ongoingCall = (FutureTypeAdapter<T>) threadCalls.get(type);
    if (ongoingCall != null) {
      return ongoingCall;
    }

    try {
      FutureTypeAdapter<T> call = new FutureTypeAdapter<T>();
      threadCalls.put(type, call);

      for (TypeAdapterFactory factory : factories) {
        TypeAdapter<T> candidate = factory.create(this, type);  // 标号1
        if (candidate != null) {
          call.setDelegate(candidate);
          typeTokenCache.put(type, candidate);
          return candidate;
        }
      }
      throw new IllegalArgumentException("GSON cannot handle " + type);
    } finally {
      threadCalls.remove(type);

      if (requiresThreadLocalCleanup) {
        calls.remove();
      }
    }
  }

  // 这里的实现很巧妙
  // 这里采用threadLocal实现了一个Cache，方便在递归调用的时候直接从cache中取值
  // 用threadlocal可以防止多线程的问题，保护cache的安全
  // 标号1所在代码行中factory.create方法中可能引起getAdapter的递归，如递归中有get了当前的type就会出现死循环。
  // 所以用FutureTypeAdapter先占位，返回占位的adapter，等到真正得到指在更新占位。
  

```

Gson 使用TypeAdapter进行用户自定义类型的解析
```
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(FileTemplate.class, new NoteTemplateAdapter());
    builder.registerTypeAdapter(Note.class, new NoteTypeAdapter());
    Gson gson = new Gson();

    // 注册一个继承体系
    public GsonBuilder registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter)
```


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface SerializedName {

  /**
   * @return the desired name of the field when it is serialized or deserialized
   */
  String value();
  /**
   * @return the alternative names of the field when it is deserialized
   */
  String[] alternate() default {};
}

SerializedName alternate 属性可以给一个变量提供多种可序列化的入口
