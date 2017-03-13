package skedgo.rxproperty

import android.databinding.ObservableField
import rx.Observable
import rx.subscriptions.Subscriptions

fun <T> ObservableField<T>.asObservable(): Observable<T> {
  return Observable.create {
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
