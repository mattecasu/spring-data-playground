package playground

;

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class SimulationTest extends Simulation {

  val app: ConfigurableApplicationContext = SpringApplication.run(classOf[App])
  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run(): Unit = app.stop()
  })

  val httpConf = http.baseURL("http://localhost:8080").doNotTrackHeader("1")

  val scn = scenario("simulation")
    .exec(
      http("POST a payment")
        .post("/payments")
        .header("Content-Type", "application/json")
        .body(StringBody(model.MockPayments.mockPayment().setId(null).toString))
    ).pause(5)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}