package skedgo.rxproperty

import android.databinding.ObservableBoolean
import rx.Observable
import rx.subscriptions.Subscriptions

fun ObservableBoolean.asObservable(): Observable<Boolean> {
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
