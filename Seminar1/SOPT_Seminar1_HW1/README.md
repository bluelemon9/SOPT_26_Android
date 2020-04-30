# <과제1 ConstraintLayout 심화학습>


## ConstraintDimensionRatio
이미지 비율 1:1로 만들기 위해 사용.
width:height 형식으로 원하는 비율을 입력한다. width나 height 중 하나의 속성은 반드시 match_constraint를 줘야 한다.

```kotlin
android:layout_width="wrap_content"
android:layout_height="0dp"
app:layout_constraintDimensionRatio="1:1"
```



## Padding
전체적으로 위아래양옆의 공간을 주기 위해 contstraintLayout에 padding을 준다.
```kotlin
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:padding="15dp"
tools:context=".MainActivity">
```



## Guideline
화면 끝까지 글이 가지 않고 원하는 비율에 맞춰 개행될 수 있도록 할 때 사용.
특정 개체의 크기를 현재 보고 있는 액티비티 화면의 비율로 조절한다. match_parent로 ConstraintLayout 를 전체 화면으로 지정해준 후, 그 안에 원하는 비율만큼의 Guideline을 넣는다.
그 후, 가이드라인에 맞춰서 원하는 개체의 Constraint 구속을 맞춰주면 원하는 비율만큼 개체의 크기를 조절할 수 있다.

```kotlin
<android.support.constraint.Guideline
    android:id="@+id/guideline"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    app:layout_constraintGuide_percent="0.85" />
```

textView의 width는 match_constraint(즉 0dp)로 해주고 guidline에 맞게 개행될 수 있게 한다.
```kotlin
android:id="@+id/textView3"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    app:layout_constraintEnd_toStartOf="@+id/guideline"
```
