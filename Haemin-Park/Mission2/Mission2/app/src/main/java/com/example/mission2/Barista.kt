package com.example.mission2

import android.util.Log
import com.example.mission2.base.BaseObserver
import com.example.mission2.base.BaseSubject
import java.lang.IllegalArgumentException

class Barista(vCallBack: ViewCallBack, pickMenu: BaseSubject, private val handler: android.os.Handler): BaseObserver {

    private var pickMenu: BaseSubject
    private val vCallBack: ViewCallBack

    init {
        Log.d("Barista","Barista init")
        this.pickMenu = pickMenu
        this.vCallBack = vCallBack
        pickMenu.registerObserver(this)
    }
    override fun update(orderData: OrderData) {
        Log.d("Barista",orderData.menuItem.getName()+" 주문 접수")
        vCallBack.updateBarista(orderData.menuItem.getName()+" 주문 접수")
        makeCoffee(orderData)
    }
    fun makeCoffee(orderData: OrderData){
        handler.postDelayed({
            Log.d("Barista",orderData.menuItem.getName()+" 제조 완료")
            vCallBack.updateBarista(orderData.menuItem.getName()+" 제조 완료")
            pickMenu.successMenu(OrderData(orderData.index, orderData.num,1, orderData.menuItem))
        }, delaytime(orderData.menuItem))
    }
    fun delaytime(menuItem: MenuItem) =
        when(menuItem.getName()){
            "ame" -> 3000L
            "cap" -> 5000L
            "car" -> 5000L
            "esp" -> 2000L
            else -> throw IllegalArgumentException("Unknown menu")
        }
}