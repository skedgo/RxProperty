package skedgo.rxproperty

import androidx.databinding.ObservableBoolean
import org.junit.Test
import rx.observers.TestSubscriber

class RxObservableBooleanKtTest {
  @Test fun shouldEmitCurrentValueAsFalse() {
    val toBeOrNotToBe = ObservableBoolean()

    val subscriber = TestSubscriber<Boolean>()
    toBeOrNotToBe.asObservable().subscribe(subscriber)

    subscriber.assertValue(false)
  }

  @Test fun shouldEmitCurrentValueAsTrue() {
    val toBeOrNotToBe = ObservableBoolean(true)

    val subscriber = TestSubscriber<Boolean>()
    toBeOrNotToBe.asObservable().subscribe(subscriber)

    subscriber.assertValue(true)
  }

  @Test fun shouldEmitFutureChanges() {
    val toBeOrNotToBe = ObservableBoolean()

    val subscriber = TestSubscriber<Boolean>()
    toBeOrNotToBe.asObservable().subscribe(subscriber)

    subscriber.assertValue(false)

    toBeOrNotToBe.set(true)
    subscriber.assertValues(false, true)

    toBeOrNotToBe.set(true)
    subscriber.assertValues(false, true)
  }
}
