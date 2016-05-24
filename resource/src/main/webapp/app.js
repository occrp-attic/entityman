var entityman = angular.module('entityman', ['ngRoute', 'ngFileUpload', 'ui.bootstrap']),
    baseUrl = 'ws';

entityman.config(['$routeProvider',
  function($routeProvider) {
  $routeProvider
    .when('/', {
      templateUrl: 'index.html',
      controller: 'IndexController',
      reloadOnSearch: true,
      resolve: {
        entities: loadEntities,
        factstats: loadFactStats
      }
    })
    .when('/file/:id', {
      templateUrl: 'file.html',
      controller: 'FileController',
      reloadOnSearch: true,
      resolve: {
        file: loadFile,
        entities: loadFileEntities
      }
    })
    .when('/entity/:type/:id', {
      templateUrl: 'entity.html',
      controller: 'EntityController',
      reloadOnSearch: true,
      resolve: {
        data: loadEntityFiles
      }
    })
  	.when('/login', {
      templateUrl: 'login.html',
      controller: 'LoginController',
      reloadOnSearch: true,
      resolve: {
        data: loadEntityFiles
      }
    });
}]);


var loadEntities = function($http) {
    console.log("loading entities");
	return $http.get(baseUrl + '/entities/workspace/default');
};

var loadFactStats = function($http) {
    console.log("loading factstats");
	return $http.get(baseUrl + '/entities/factstats/default');
};

var loadFileEntities = function($http, $route) {
  var fileId = $route.current.params.id;
  return $http.get(baseUrl + '/entities/AllByFileId/' + fileId);
};


var loadFile = function($http, $route) {
  var fileId = $route.current.params.id;
  return $http.get(baseUrl + '/entities/byId/IngestedFile/' + fileId);
};


var loadEntityFiles = function($http, $q, $route, $sce) {
  var entityId = $route.current.params.id,
      entityType = $route.current.params.type,
      result = {files: []},
      done = $q.defer();
  $q.all([
    $http.get(baseUrl + '/entities/byId/' + entityType + '/' + entityId),
    $http.get(baseUrl + '/entities/getFacts/' + entityType + '/' + entityId)
  ]).then(function(res) {
    result.entity = res[0].data.o;
    result.facts = res[1].data.o.map(function(fact) {
      var excerpt = fact.data.excerpt
      excerpt = excerpt.replace(result.entity.label, '<span class="hlt">' + result.entity.label + '</span>');
      if (fact.position > 0) {
        excerpt = '<span class="ell">...</span> ' + excerpt;
      }
      excerpt = excerpt + ' <span class="ell">...</span>';
      fact.snippet = $sce.trustAsHtml(excerpt);
      return fact;
    });
    var https = result.entity.fileIds.map(function(fileId) {
      return $http.get(baseUrl + '/entities/byId/IngestedFile/' + fileId);
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


entityman.directive('fileListing', function () {
  return {
    restrict: 'E',
    scope: {
      files: '=',
      facts: '='
    },
    templateUrl: 'file_listing.html',
    link: function (scope, element, attrs, model) {

      scope.getDownloadUrl = function(fileId) {
        return baseUrl + '/entities/getFile/' + fileId;
      };

      scope.getFacts = function(fileId) {
        var facts = [];
        scope.facts.forEach(function(fact) {
          if (fact.fileIds.indexOf(fileId) != -1) {
            facts.push(fact);
          }
        });
        return facts;
      };

    }
  };
});


entityman.directive('entityListing', function () {
  return {
    restrict: 'E',
    scope: {
        factstats: '=',
        entities: '=',
        files: '=',
    },
    templateUrl: 'entity_listing.html',
    link: function (scope, element, attrs, model) {
//      console.log("entities ax : %o",scope.entities);
//      console.log("scope.factstats ax : %o",scope.factstats);
      scope.shownEntities = [];

      scope.$watch('entities', function(entities) {
        if (!entities) return;

        var matching = [];
        entities.forEach(function(e) {
          if (['Fact', 'IngestedFile'].indexOf(e.type) == -1) {
            matching.push(e);
          }
        });

        scope.shownEntities = matching.sort(function(a, b) {
          var dist = b.fileIds.length - a.fileIds.length;
          if (dist == 0) {
            return a.label.localeCompare(b.label);
          }
          return dist;
        });

      });
        
      console.log("factstats scope x : %o",scope);
      console.log("factstats xx : %o", scope.factstats);

//      scope.factstats = {};
//      scope.$watch('factstats', function(factstats) {
//          console.log("factstats xx : %o", factstats);
//            if (!factstats) return;
//            scope.factstats = factstats;
//      });
      
      console.log("factcount xxx : %o", scope.factcount);
    }
  };
});


entityman.controller('BaseController', function ($scope, $rootScope, $modal) {
  $rootScope.routeLoading = true;

  $rootScope.$on("$routeChangeStart", function (event, next, current) {
    $rootScope.routeLoading = true;
  });

  $rootScope.$on("$routeChangeSuccess", function (event, next, current) {
    $rootScope.routeLoading = false;
  });

  $scope.uploadFile = function() {
    var d = $modal.open({
      templateUrl: 'upload.html',
      controller: 'UploadController'
    });
  };
});


entityman.controller('UploadController', function($scope, $location, $modalInstance, Upload) {
  $scope.files = [];
  $scope.filenames = '';
  $scope.uploadMessage = null;

  $scope.close = function() {
    $modalInstance.dismiss('cancel');
  };

  $scope.setFiles = function(files) {
    $scope.files = files;
    $scope.uploadMessage = null;
    var names = $scope.files.map(function(f) {
      return f.name;
    });
    $scope.filenames = names.join(', ');
  };

  $scope.upload = function() {
    Upload.upload({
      url: baseUrl + '/entities/ingestSync/default',
      file: $scope.files
    }).progress(function (evt) {
      var pct = parseInt((evt.loaded / evt.total) * 100);
      if (pct >= 99) {
        $scope.uploadMessage = 'Processing the document...';
      } else {
        $scope.uploadMessage = pct + '% uploaded';
      }
    }).success(function (data, status, headers, config) {
      for (var i in data.o) {
        var entity = data.o[i];
        if (entity['class'].indexOf('IngestedFile') != -1) {
          $location.path('/file/' + entity.id);
        }
      }
      $modalInstance.close('ok');
    }).error(function (data, status, headers, conf) {
      $scope.setFiles([]);
    });
  };
});


entityman.controller('IndexController', function ($scope, $location, entities,factstats) {
  var results = [];
  angular.forEach(entities.data.o, function(entities, type) {
    if (angular.isArray(entities)) {
      angular.forEach(entities, function(entity) {
        if (entity.fileIds) {
          entity.type = type;
          results.push(entity);
        }
      });
    };
  });
  $scope.entities = results;
  $scope.facts = [];
  $scope.files = entities.data.o.IngestedFile;
  $scope.factstats = factstats.data.o;
  console.log("factstats z : %o", $scope.factstats);
});


entityman.controller('FileController', function ($scope, $location, $routeParams, file, entities) {
  var fileId = $routeParams.id;
  $scope.file = file.data.o;
  $scope.fileId = fileId;

  var results = [];
  angular.forEach(entities.data.o, function(entities, type) {
    if (angular.isArray(entities)) {
      angular.forEach(entities, function(entity) {
        entity.type = type;
        results.push(entity);
      });
    };
  });
  $scope.entities = results;
});


entityman.controller('EntityController', function ($scope, $location, $routeParams, data) {
  var entityId = $routeParams.id;
  $scope.entityType = $routeParams.type;
  $scope.entity = data.entity;
  $scope.facts = data.facts;
  $scope.files = data.files;
});

entityman.controller('LoginController', function ($scope, $location, $routeParams, data) {
});
