package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok("Your new application is ready. \n\n\n\n Description : \n Samantha is looking for a house to rent for her holidays. She has a profile on a vacation rentals website and has previously rented and rated several holiday packages. Sam is looking for recommendations based on her preferences and tastes. The system should already know Sam’s tastes. Apparently she seems to like accommodations of the type house, based on her rating page. The system should recommend something similar. \n\n\n add /samantha to url [ e.g. localhost:9000/samantha ] to view the predicted recommendations according to samantha's taste in the json format \n\n\n use case is extended for multiple users add /userID/[put a userid here] to view the predicted recommendations according to specific user's taste in the json format ")
  }

}
