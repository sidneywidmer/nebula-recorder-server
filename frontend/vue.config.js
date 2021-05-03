const path = require("path");

module.exports = {
    css: {
        loaderOptions: {
            sass: {
                prependData: '@import "@/assets/styles/style.scss";',
            }
        }
    },
    outputDir: path.resolve(__dirname, "../app/src/main/resources/templates"),
    assetsDir: "../static"
}