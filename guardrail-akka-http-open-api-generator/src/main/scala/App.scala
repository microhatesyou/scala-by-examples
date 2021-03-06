import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.github.microhatesyou.generated.definitions.Pet
import com.github.microhatesyou.generated.pet.{PetHandler, PetResource}

import scala.concurrent._
import scala.concurrent.duration.Duration

object App extends App {

  implicit def actorSystem: ActorSystem = ActorSystem()

  val routes = PetResource.routes(new PetHandler {
    override def updatePet(respond: PetResource.UpdatePetResponse.type)(name: String, body: Pet): Future[PetResource.UpdatePetResponse] =
      Future.successful(respond.OK(body))

    override def createPet(respond: PetResource.CreatePetResponse.type)(body: Pet, requestId: String): Future[PetResource.CreatePetResponse] = Future.successful {
      PetService.createPet(body, requestId).fold(respond.BadRequest, respond.OK)
    }

  })

  Await.result(Http().newServerAt("127.0.0.1", 8080).bindFlow(routes), Duration.Inf)
  println("Running at http://localhost:8080 !")
}
