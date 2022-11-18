import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.Request
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter

@Suppress("unused")
class Handler : RequestStreamHandler {
    private val json:Json =Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }


    @Throws(IOException::class)
    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val inputText = input.reader().readText()
        context.logger.log("input: $inputText")
        val obj = json.decodeFromString<Request>(inputText)
        val txt = json.encodeToString(obj)
        val writer = OutputStreamWriter(output)
        writer.write(txt)
        writer.close()
    }
}