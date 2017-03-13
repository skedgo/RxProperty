# RxProperty
[![](https://jitpack.io/v/skedgo/RxProperty.svg)](https://jitpack.io/#skedgo/RxProperty)

RxJava binding APIs for observable fields and observable collections from the Data Binding Library

## Usage
A main feature of this library is to provide some extension functions `asObservable()` to model the changes of observable properties via RxJava's `Observable`. The library is specially helpful for the cases of implementing dependent properties. For example, assume that we have a `PersonViewModel` that specifies `firstName` and `lastName`. Both are represented by `ObservableField<String>`. There is another `fullName` that is computed based on `firstName` and `lastName`. Whenever one of the 2 properties is updated, `fullName` should be updated accordingly. By utilizing the `asObservable()`, one approach can be:

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
