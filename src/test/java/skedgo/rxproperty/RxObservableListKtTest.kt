package skedgo.rxproperty

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import org.junit.Test
import rx.observers.TestSubscriber

class RxObservableListKtTest {
  @Test fun shouldEmitLatestValue() {
    val list: ObservableList<Int> = ObservableArrayList()
    list.addAll(listOf(1, 2, 3))

    val subscriber = TestSubscriber<ObservableList<Int>>()
    list.asObservable().subscribe(subscriber)

    subscriber.assertValue(list)
  }

  @Test fun shouldNotifyAddition() {
    val list: ObservableList<Int> = ObservableArrayList()

    val subscriber = TestSubscriber<ObservableList<Int>>()
    list.asObservable().subscribe(subscriber)

    list.add(1)
    subscriber.assertValues(list, list)
  }

  @Test fun shouldNotifyRemoval() {
    val list: ObservableList<Int> = ObservableArrayList()
    list.addAll(listOf(1, 2, 3))

    val subscriber = TestSubscriber<ObservableList<Int>>()
    list.asObservable().subscribe(subscriber)

    list.removeAt(0)
    subscriber.assertValues(list, list)
  }

  @Test fun shouldNotifyClear() {
    val list: ObservableList<Int> = ObservableArrayList()
    list.addAll(listOf(1, 2, 3))

    val subscriber = TestSubscriber<ObservableList<Int>>()
    list.asObservable().subscribe(subscriber)

    list.clear()
    subscriber.assertValues(list, list)
  }
}
