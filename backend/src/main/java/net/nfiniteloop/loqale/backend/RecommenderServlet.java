package net.nfiniteloop.loqale.backend;

import javax.servlet.http.HttpServlet;

/**
 * Created by vaek on 10/5/14.
 */
public class RecommenderServlet extends HttpServlet{
    /* Working thoughts
          Think Thread Safety!!
          Think runtime!!
          Algorithm
            Build User Profile
            Raw Recommendation Phase
                Is this a Cold Start?
                    Yes: Use Levy Flight Pattern to get raw recommendations
                        For each point on Path
                            Get establishment by nearest location and query by category
                            Return weighted results for category
                                (Make sure to handle collisions)

                    No: Use Profile to get raw recommendations
                        For each CheckIn
                            Get establishments category and query by category
                            Return weighted results for category
                        For each Tag
                            Get Label and query by label
                            return results by currency
            Filter Phase
                If Friendships
                    CF Friends to mark raw CheckIn recommendations
                    Repeat for Tags
                If CheckIns
                    Build Levy Flight model and determine distance/frequency weights
                    Mark results with high distance/frequency weights

                If Tags
                    Mark Results with high label frequency/or favorites
            Post Processing
                Generate Reason info for marked data
                Package marked recommendations
            Return recommendation package.
    */

}
