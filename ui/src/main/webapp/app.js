var entityman = angular.module('entityman', [ 'ngRoute', 'ngFileUpload',
		'ui.bootstrap', 'angular-growl' ]);
var baseUrl = '../resource/ws';

entityman.config([ '$routeProvider','growlProvider', function($routeProvider, growlProvider) {
	growlProvider.globalTimeToLive({success: 1500, error: 5000, warning: 4000, info: 4000});
	growlProvider.globalPosition('bottom-right');
	$routeProvider.when('/', {
		templateUrl : 'index.html',
		controller : 'IndexController',
		reloadOnSearch : true,
		resolve : {
		// workspaces: loadWorkspaces,
		// entities: loadEntities,
		// factstats: loadFactStats
		}
	}).when('/file/:id', {
		templateUrl : 'partials/view_file.html',
		controller : 'FileController',
		reloadOnSearch : true, 
		resolve : {
//			file : loadFile,
//			entities : loadFileEntities,
//	    factstats: loadFactStats
		}
	}).when('/entity/:type/:id', {
		templateUrl : 'entity.html',
		controller : 'EntityController',
		reloadOnSearch : true,
		resolve : {
			data : loadEntityFiles
		}
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'LoginController',
		reloadOnSearch : true,
		resolve : {
			data : loadEntityFiles
		}
	});
} ]);

// var loadWorkspaces = function(entitymanWs) {
// return entitymanWs.loadWorkspaces();
// };
//
// var loadEntities = function($http) {
// var activeWorkspaceName = (typeof activeWorkspace === "undefined") ?
// "default" : activeWorkspace.name;
// console.log("loading entities for workspace : %s",activeWorkspaceName);
// return $http.get(baseUrl + '/entities/workspace/'+activeWorkspaceName);
// };
//
// var loadFactStats = function($http) {
// var activeWorkspaceName = (typeof activeWorkspace === "undefined") ?
// "default" : activeWorkspace.name;
// console.log("loading factstats for workspace : %s",activeWorkspaceName);
// return $http.get(baseUrl + '/entities/factstats/'+activeWorkspaceName);
// };

var loadFileEntities = function($http, $route) {
	var fileId = $route.current.params.id;
	return $http.get(baseUrl + '/entities/AllByFileId/' + fileId);
};

var loadFile = function($http, $route) {
	var fileId = $route.current.params.id;
	return $http.get(baseUrl + '/entities/byId/IngestedFile/' + fileId);
};

var loadEntityFiles = function($http, $q, $route, $sce) {
	var entityId = $route.current.params.id, entityType = $route.current.params.type, result = {
		files : []
	}, done = $q.defer();
	$q.all(
			[
					$http.get(baseUrl + '/entities/byId/' + entityType + '/'
							+ entityId),
					$http.get(baseUrl + '/entities/getFacts/' + entityType
							+ '/' + entityId) ]).then(
			function(res) {
				result.entity = res[0].data.o;
				result.facts = res[1].data.o.map(function(fact) {
					var excerpt = fact.data.excerpt
					excerpt = excerpt.replace(result.entity.label,
							'<span class="hlt">' + result.entity.label
									+ '</span>');
					if (fact.position > 0) {
						excerpt = '<span class="ell">...</span> ' + excerpt;
					}
					excerpt = excerpt + ' <span class="ell">...</span>';
					fact.snippet = $sce.trustAsHtml(excerpt);
					return fact;
				});
				var https = result.entity.fileIds.map(function(fileId) {
					return $http.get(baseUrl + '/entities/byId/IngestedFile/'
							+ fileId);
				});
				$q.all(https).then(function(files) {
					files.forEach(function(file) {
						result.files.push(file.data.o);
					});
					done.resolve(result);
				});
			});
	return done.promise;
};
