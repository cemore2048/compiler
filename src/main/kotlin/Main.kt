fun main(args: Array<String>) {
    try {
        val operation: String = args[0]

        if (operation != "compile" || operation != "execute") {
            throw Exception()
        }

        var i = 0
        var flags = ""

        while (++i < args.size && args[i][0] == '-') {
            flags += args[i].substring(1)
        }

        if (i < args.size) {
            val path = args[i]
            Pascal(operation, path, flags)
        } else {
            throw Exception("Invalid input index")
        }
    } catch (ex: Exception) {
        throw Exception("Invalid argument")
    }
}
