module.exports = {
	outputDir: "target/static",
	chainWebpack: (config) => {
		config.plugins.delete("preload-index");
		config.plugin("html-index").tap((args) => {
			args[0].minify.removeAttributeQuotes = false;
			return args;
		});
	},
	publicPath: "",
	pages: {
		index: {
			// entry for the page
			entry: "src/index/main.js",
			// the source template
			template: "../backend/src/main/resources/raw/index.mustache",
			// output as dist/index.html
			filename: "../templates/index.mustache",
			// chunks to include on this page, by default includes
			// extracted common chunks and vendor chunks.
			chunks: ["chunk-vendors", "chunk-common", "index"],
		},
	},
	runtimeCompiler: true,
};
