package androiddeveloper.amrrabbie.kotlinapimps.viewmodel

import android.util.Log
import androiddeveloper.amrrabbie.kotlinapimps.model.directions.DirectionsResponse
import androiddeveloper.amrrabbie.kotlinapimps.model.nearestby.ResultsItem
import androiddeveloper.amrrabbie.kotlinapimps.repostry.NearestByRepostry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearestByViewModel
@Inject
constructor(private val nearestByRepostry: NearestByRepostry):ViewModel()
{
    var nearestbylist= MutableLiveData<List<ResultsItem>>()

     var directions=MutableLiveData<DirectionsResponse>()



    fun getNearestBy(location:String,radiuss:Int,types:String,names:String,key:String)=viewModelScope.launch {
        nearestByRepostry.getNearestBy(location,radiuss,types,names,key).let { response->
            if(response.isSuccessful){
                var list:List<ResultsItem>?=response.body()!!.results
                nearestbylist.postValue(list)
            }else{
                Log.d("TAG", "getNearestBy: "+response.message())
            }
        }
    }

    fun getDirections(origins:String,destion:String,key:String)=viewModelScope.launch {
        nearestByRepostry.getDirections(origins,destion,key).let { responsee->
            if(responsee.isSuccessful){
                directions.postValue(responsee.body())
            }else{
                Log.d("TAG", "getDirections: ${responsee.message()}")
            }
        }
    }
}