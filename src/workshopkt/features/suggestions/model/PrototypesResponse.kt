package workshopkt.features.suggestions.model

/**
 * The response has the following structure
 * {
 *    data: <array of prototypes>,
 *    paging: {
 *      total: <number>
 *      page: <number>
 *    }
 * }
 */
data class PrototypesResponse(val data: List<Prototype>)
