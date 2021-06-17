package androiddeveloper.amrrabbie.kotlinapimps.repostry

import androiddeveloper.amrrabbie.kotlinapimps.model.nearestby.NearestByResponse
import androiddeveloper.amrrabbie.kotlinapimps.network.NearestByApiServices
import javax.inject.Inject

class NearestByRepostry
@Inject
constructor(private val nearestByApiServices: NearestByApiServices)
{

   public suspend fun getNearestBy(location:String,radius:Int,types:String,names:String,key:String)=
        nearestByApiServices.getNearestBy(location,radius,types,names,key)

    public suspend fun getDirections(origins:String,destion:String,key:String)=
        nearestByApiServices.getDirections(origins,destion,key)


}