package app

import ratpack.file.MimeTypes
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.path.PathBinding

class WebJarsHandler implements Handler {
    @Override
    void handle(Context context) {
        def path = context.maybeGet(PathBinding)?.pastBinding ?: context.request.path
        def resourceStream = loadResource(path)
        if (resourceStream) {
            context.response.send(context.get(MimeTypes).getContentType(path), resourceStream)
        } else {
            context.next()
        }
    }

    private InputStream loadResource(String path) {
        return getClass().getResourceAsStream("/META-INF/resources/webjars/${path}")
    }
}
