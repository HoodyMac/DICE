(function (global) {
  System.config({
    paths: {
      'npm:': 'node_modules/'
    },
    map: {
      app: 'app',
      'ng2-modal': 'node_modules/ng2-modal',
      'ng2-translate': 'npm:ng2-translate/bundles/ng2-translate.umd.js',
      'lodash': 'npm:lodash/lodash.js',
      '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
      '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
      '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
      '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
      '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
      '@angular/http': 'npm:@angular/http/bundles/http.umd.js',
      '@angular/router': 'npm:@angular/router/bundles/router.umd.js',
      '@angular/forms': 'npm:@angular/forms/bundles/forms.umd.js',
      'ngx-monaco-editor': 'npm:ngx-monaco-editor/editor.umd.js',
      'rxjs': 'npm:rxjs'
    },
    packages: {
      app: {
        main: './main.js',
        defaultExtension: 'js'
      },
      rxjs: {
        defaultExtension: 'js'
      },
      'ng2-translate' : { defaultExtension: 'js' },
      "ng2-modal": { "main": "index.js", "defaultExtension": "js"}
    }
  });
})(this);
