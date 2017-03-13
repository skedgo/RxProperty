package skedgo.rxproperty

import android.databinding.ObservableField
import rx.Observable
import rx.subscriptions.Subscriptions

/**
 * When we subscribe to an [Observable] created by this function,
 * the [Observable] will emit the current value of [ObservableField]
 * and also emit any future changes.
 */
fun <T> ObservableField<T>.asObservable(): Observable<T> {
  return Observable.create {
    // To emit the current value.
    it.onNext(get())
    val callback = object : android.databinding.Observable.OnPropertyChangedCallback() {
      override fun onPropertyChanged(sender: android.databinding.Observable?, propertyId: Int) {
        it.onNext(get())
      }
    }
    addOnPropertyChangedCallback(callback)
    it.add(Subscriptions.create { removeOnPropertyChangedCallback(callback) })
  }
}
