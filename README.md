# RxProperty
[![](https://jitpack.io/v/skedgo/RxProperty.svg)](https://jitpack.io/#skedgo/RxProperty) [![Build Status](https://travis-ci.org/skedgo/RxProperty.svg?branch=master)](https://travis-ci.org/skedgo/RxProperty) <a href="http://www.methodscount.com/?lib=com.github.skedgo%3ARxProperty%3Av1.3"><img src="https://img.shields.io/badge/Methods and size-47 | 13 KB-e91e63.svg"/></a>

RxJava binding APIs for observable fields and observable collections from the Data Binding Library

## Usage
A main feature of this library is to provide some extension functions `asObservable()` to model the changes of observable fields and observable collections via [RxJava](https://github.com/ReactiveX/RxJava)'s `Observable`. The library is specially helpful for the cases of implementing dependent properties. For example, assume that we have a `PersonViewModel` that specifies `firstName` and `lastName`. Both are represented by `ObservableField<String>`. There is another `fullName` that is computed based on `firstName` and `lastName`. Whenever one of the 2 properties is updated, `fullName` should be updated accordingly. By utilizing `asObservable()` functions, an implementation can be:

```kotlin
class PersonViewModel {
  val firstName = ObservableField<String>()
  val lastName = ObservableField<String>()
  val fullName = ObservableField<String>()

  init {
    Observable.combineLatest(
      firstName.asObservable(),
      lastName.asObservable(), {
      firstName, lastName ->
      if (firstName.isNullOrEmpty() && lastName.isNullOrEmpty()) {
        return firstName
      } else if (firstName.isNullOrEmpty()) {
        return lastName
      } else if (lastName.isNullOrEmpty()) {
        return firstName
      } else {
        return "${firstName}, ${lastName}"
      }
    }).subscribe { fullName.set(it) }
  }
}
```

That is achieved because when we subscribe to an `Observable` created by `asObservable()`, the `Observable` will emit the current value of `ObservableField<String>` and also emit any future changes. But what if we are only interested in observing future changes without the current value? Easy, just `skip(1)`, for instance:

```kotlin
val onFullNameChanged = fullName.asObservable().skip(1)
```

As of now, the library only supports [`ObservableBoolean`](https://developer.android.com/reference/android/databinding/ObservableBoolean.html), [`ObservableField`](https://developer.android.com/reference/android/databinding/ObservableField.html), and [`ObservableList`](https://developer.android.com/reference/android/databinding/ObservableList.html) due to our current need. In the future, we may add more `asObservable()` functions for other kinds. That said, we welcome any external contribution. Pull requests are very highly appreciated.
