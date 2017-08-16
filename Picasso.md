# Picasso

## fetures
- Handling ImageView recycling and download cancelation in an adapter.
- Complex image transformations with minimal memory use.
- Automatic memory and disk caching.

## Thread 
- 开几个线程？  
根据网络类型开不同的线程
- 2G 1个
- 3G 2个
- 4G 3个
- WiFi 4个

## Memcache

### cache大小

```
  static int calculateMemoryCacheSize(Context context) {
    ActivityManager am = getService(context, ACTIVITY_SERVICE);
    boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
    int memoryClass = largeHeap ? am.getLargeMemoryClass() : am.getMemoryClass();
    // Target ~15% of the available heap.
    return (int) (1024L * 1024L * memoryClass / 7);
  }
  ```
  1/7 15% 的内存大小

### cache key

```
  static String createKey(Request data, StringBuilder builder) {
    if (data.stableKey != null) {
      builder.ensureCapacity(data.stableKey.length() + KEY_PADDING);
      builder.append(data.stableKey);
    } else if (data.uri != null) {
      String path = data.uri.toString();
      builder.ensureCapacity(path.length() + KEY_PADDING);
      builder.append(path);
    } else {
      builder.ensureCapacity(KEY_PADDING);
      builder.append(data.resourceId);
    }
    builder.append(KEY_SEPARATOR);

    if (data.rotationDegrees != 0) {
      builder.append("rotation:").append(data.rotationDegrees);
      if (data.hasRotationPivot) {
        builder.append('@').append(data.rotationPivotX).append('x').append(data.rotationPivotY);
      }
      builder.append(KEY_SEPARATOR);
    }
    if (data.hasSize()) {
      builder.append("resize:").append(data.targetWidth).append('x').append(data.targetHeight);
      builder.append(KEY_SEPARATOR);
    }
    if (data.centerCrop) {
      builder.append("centerCrop:").append(data.centerCropGravity).append(KEY_SEPARATOR);
    } else if (data.centerInside) {
      builder.append("centerInside").append(KEY_SEPARATOR);
    }

    if (data.transformations != null) {
      //noinspection ForLoopReplaceableByForEach
      for (int i = 0, count = data.transformations.size(); i < count; i++) {
        builder.append(data.transformations.get(i).key());
        builder.append(KEY_SEPARATOR);
      }
    }

    return builder.toString();
  }
```

## DiskCache

### 大小
```
  private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
  private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

  @TargetApi(JELLY_BEAN_MR2)
  static long calculateDiskCacheSize(File dir) {
    long size = MIN_DISK_CACHE_SIZE;

    try {
      StatFs statFs = new StatFs(dir.getAbsolutePath());
      //noinspection deprecation
      long blockCount =
          SDK_INT < JELLY_BEAN_MR2 ? (long) statFs.getBlockCount() : statFs.getBlockCountLong();
      //noinspection deprecation
      long blockSize =
          SDK_INT < JELLY_BEAN_MR2 ? (long) statFs.getBlockSize() : statFs.getBlockSizeLong();
      long available = blockCount * blockSize;
      // Target 2% of the total space.
      size = available / 50;
    } catch (IllegalArgumentException ignored) {
    }

    // Bound inside min/max size for disk cache.
    return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
  }
```
最小5Ｍ，最大５０Ｍ或者２％目标分区大小
### 位置


## support URI type
- 通讯录头像
- MediaStore
- ContentProvider
- 本地文件
- assets
- 网络文件
- 自定义source

## 图片裁剪

### auto
如果RequestCreator没有使用resize指定大小，decode原图。
否则参考指定的大小按照以下规则decode

```
  static void calculateInSampleSize(int reqWidth, int reqHeight, int width, int height,
      BitmapFactory.Options options, Request request) {
    int sampleSize = 1;
    if (height > reqHeight || width > reqWidth) {
      final int heightRatio;
      final int widthRatio;
      if (reqHeight == 0) {
        sampleSize = (int) Math.floor((float) width / (float) reqWidth);
      } else if (reqWidth == 0) {
        sampleSize = (int) Math.floor((float) height / (float) reqHeight);
      } else {
        heightRatio = (int) Math.floor((float) height / (float) reqHeight);
        widthRatio = (int) Math.floor((float) width / (float) reqWidth);
        sampleSize = request.centerInside
            ? Math.max(heightRatio, widthRatio)
            : Math.min(heightRatio, widthRatio);
      }
    }
    options.inSampleSize = sampleSize;
    options.inJustDecodeBounds = false;
  }
```

### fit
如果指定了fit模式，会等到绘制的时候根据view的真实大小进行decode


## 后期处理
```
public RequestCreator transform(Transformation transformation)
public RequestCreator transform(List<? extends Transformation> transformations)
```

## 加载动画
```
public RequestCreator noFade()
```

### 重试
重试2次

### 取消
如果下载过程已开始，不会取消下载






