import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.Request
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.ScanRequest
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

    private val client = DynamoDbClient.builder().apply {
        credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        region(Region.AP_NORTHEAST_1)
    }.build()


    @Throws(IOException::class)
    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val inputText = input.reader().readText()
        context.logger.log("input: $inputText")
        val obj = json.decodeFromString<Request>(inputText)
        val txt = json.encodeToString(obj)
        val writer = OutputStreamWriter(output)
        val req = ScanRequest.builder().apply {
            this.tableName("JoinCheckerDev")
        }.build()
        print(client.scan(req).count())
        writer.write(txt)
        writer.close()
    }
}