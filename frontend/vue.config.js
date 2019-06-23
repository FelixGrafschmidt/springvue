const StatsPlugin = require("stats-webpack-plugin");

module.exports = {
	outputDir: "target",
	chainWebpack: (config) => {
		config.plugins.delete("preload-index");
		config.plugin("html-index").tap((args) => {
			if (args[0].minify) {
				args[0].minify.removeAttributeQuotes = false;
			}
			return args;
		});
	},
	configureWebpack: {
		plugins: [
			new StatsPlugin("stats.json", {
				assets: true,
				assetsSort: "chunk",
				chunks: false,
				modules: false,
				version: false,
				hash: false,
				publicPath: false,
				outputPath: false,
				filteredAssets: false,
				entrypoints: false,
				namedChunkGroups: false,
				children: false,
			}),
		],
	},
	publicPath: "",
	pages: {
		index: {
			// entry for the page
			entry: "src/index/main.js",
			// the source template
			template: "../backend/src/main/resources/templates/index.mustache",
			// output as dist/index.html
			filename: "index.mustache",
			// chunks to include on this page, by default includes
			// extracted common chunks and vendor chunks.
			chunks: ["chunk-vendors", "chunk-common", "index"],
		},
	},
	runtimeCompiler: true,
	devServer: {
		proxy: {
			"/": {
				target: "http://localhost:8082",
				ws: false,
				changeOrigin: true,
			},
		},
		publicPath: "/js/"
	},
};
