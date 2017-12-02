package hammock
package akka

import _root_.akka.actor.ActorSystem
import _root_.akka.http.scaladsl.HttpExt
import _root_.akka.http.scaladsl.model.{HttpRequest => AkkaRequest, HttpResponse => AkkaResponse, Uri => AkkaUri}
import _root_.akka.stream.ActorMaterializer
import cats._
import cats.effect.IO
import cats.free.Free
import hammock.free.algebra._
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

import scala.concurrent.{ExecutionContext, Future}

class InterpreterSpec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter {
  implicit val system: ActorSystem    = ActorSystem("test")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext   = ExecutionContext.Implicits.global

  val client = mock[HttpExt]
  val interp = new AkkaInterpreter[IO](client)

  after {
    reset(client)
  }

  "akka http interpreter" should {

    "create a correct AkkaResponse from akka's Http response" in {
      val hammockReq = Get(HttpRequest(Uri(path = "http://localhost:8080"), Map(), None))
      val akkaReq    = AkkaRequest(uri = AkkaUri("http://localhost:8080"))
      when(client.singleRequest(akkaReq)).thenReturn(Future.successful(httpResponse))
      val result = (Free.liftF(hammockReq) foldMap interp.trans).unsafeRunSync

      result shouldEqual HttpResponse(Status.OK, Map(), Entity.StringEntity(""))
    }

  }

  private[this] def httpResponse: AkkaResponse =
    AkkaResponse()

}
