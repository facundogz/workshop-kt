package workshopkt.features.suggestions

object SuggestionsService : (String) -> List<Suggestion> {
    override fun invoke(hint: String) = PrototypesClient.prototypeList
        .filter { it.destination.name.contains(hint,true) }
        .groupBy({ it.destination.name to it.destination.id }) { it.id }
        .map {(pair, ids) ->
            Suggestion(
                pair.first,
                hint.takeUnless { it.isEmpty() }?.let {
                    pair.first.replace(Regex(it, RegexOption.IGNORE_CASE))  { "<em>${it.value}</em>" }
                } ?: pair.first,
                pair.second,
                ids
            )
        }
}