package skedgo.rxproperty

import androidx.databinding.ObservableBoolean
import rx.Observable
import rx.subscriptions.Subscriptions

/**
 * When we subscribe to an [Observable] created by this function,
 * the [Observable] will emit the current value of [ObservableBoolean]
 * and also emit any future changes.
 */
fun ObservableBoolean.asObservable(): Observable<Boolean> {
  return Observable.create {
    // To emit the current value.
    it.onNext(get())
    val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
      override fun onPropertyChanged(sender: androidx.databinding.Observable?, propertyId: Int) {
        it.onNext(get())
      }
    }
    addOnPropertyChangedCallback(callback)
    it.add(Subscriptions.create { removeOnPropertyChangedCallback(callback) })
  }
}
