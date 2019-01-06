package controllers

import com.intel.analytics.bigdl.dataset.Sample
import com.intel.analytics.bigdl.numeric.NumericFloat
import com.intel.analytics.bigdl.optim.LocalPredictor
import com.intel.analytics.bigdl.tensor.Tensor
import javax.inject._
import models.LoadModel
import play.api.libs.json._
import play.api.mvc._
import play.libs.F.Tuple
import utilities.Helper._

import scala.collection.immutable.Map

/**
  * This controller creates an `Action` to handle recommendation prediction.
  */

@Singleton
class ModelController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with LoadModel {

  /**
    * Create an Action to return @ModelController status.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `POST` request with
    * a path of `/recModel`.
    */

  def recModel: Action[JsValue] = Action(parse.json) { request =>
    try {
      val requestJson = request.body.toString()
      val requestMap = mapper.readValue(requestJson, classOf[Map[String, Any]])
      val sku = requestMap("SKU_NUM").asInstanceOf[List[String]]
      sku.foreach(
        println(_)
      )
      val skuIndex = sku.map(x => leapTransform(
        requestString = x, inputCol = "SKU_NUM", outputCol = "SKU_INDEX", transformer = params.skuIndexerModel.get, mapper = mapper
      ).toFloat).reverse.padTo(10, 0f).reverse

      val inputSample = Array(Sample(Tensor(skuIndex.toArray, Array(10))))

      val predict = params.recModel match {
        case Some(_) =>
          val predict = LocalPredictor(params.recModel.get).predict(inputSample)
            .map { x =>
              val _output = x.toTensor[Float]
              val indices = _output.topk(5, 1, false)
              val predict1 = indices._2.valueAt(1).toInt - 1
              val predict2 = indices._2.valueAt(2).toInt - 1
              val predict3 = indices._2.valueAt(3).toInt - 1
              val predict4 = indices._2.valueAt(4).toInt - 1
              val predict5 = indices._2.valueAt(5).toInt - 1
              val probability1 = Math.exp(_output.valueAt(predict1).toDouble)
              val probability2 = Math.exp(_output.valueAt(predict2).toDouble)
              val probability3 = Math.exp(_output.valueAt(predict3).toDouble)
              val probability4 = Math.exp(_output.valueAt(predict4).toDouble)
              val probability5 = Math.exp(_output.valueAt(predict5).toDouble)
              Array(
                Map("predict1" -> revertStringIndex(predict1.toString), "probability1" -> probability1),
                Map("predict2" -> revertStringIndex(predict2.toString), "probability2" -> probability2),
                Map("predict3" -> revertStringIndex(predict3.toString), "probability3" -> probability3),
                Map("predict4" -> revertStringIndex(predict4.toString), "probability4" -> probability4),
                Map("predict5" -> revertStringIndex(predict5.toString), "probability5" -> probability5)
              )
            }.head
          predict
        case None => Map("predict" -> "N/A", "probability" -> 0.0)
      }

      val predictionJson = mapper.writeValueAsString(predict)

      Ok(Json.parse(predictionJson.toString))
    }

    catch{
      case _:Exception => BadRequest("Nah nah nah nah nah...this request contains bad characters...")
    }
  }
}
