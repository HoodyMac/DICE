var gulp        = require('gulp'),
    path        = require('path'),
    browserSync = require('browser-sync'),
    cache       = require('gulp-cache'),
    del         = require('del'),
    /* CSS */
    less        = require('gulp-less'),
    cssnano     = require('gulp-cssnano'),
    rename      = require('gulp-rename'),
    /* JS & TS */
    jsuglify    = require('gulp-uglify'),
    typescript  = require('gulp-typescript'),
    sourcemaps  = require('gulp-sourcemaps'),
    /* Images */
    imagemin    = require('gulp-imagemin');

    /* Path variables */
var assetsDev   = 'assets/',
    assetsProd  = 'app/',
    appDev = 'dev/',
    appProd = 'app/';

/* Mixed */
var ext_replace = require('gulp-ext-replace');

var tsProject = typescript.createProject('tsconfig.json');

gulp.task('build-less', function () {
  return gulp.src(assetsDev + 'less/**/*.less')
    .pipe(less({
      paths: [ path.join(__dirname, 'less', 'includes') ]
    }))
    .pipe(gulp.dest(assetsProd + 'css/'))
    .pipe(browserSync.stream());
});
gulp.task('build-less-in-dev', function () {
    return gulp.src(appDev + '**/*.less')
        .pipe(less({
            paths: [ path.join(__dirname, 'less', 'includes') ]
        }))
        .pipe(gulp.dest(assetsProd))
        .pipe(browserSync.stream());
});

gulp.task('css-min', ['build-less', 'build-less-in-dev'], function () {
    return gulp.src([
        appProd + '**/*.css'
    ])
        .pipe(cssnano())
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest(appProd));
});

gulp.task('build-ts', function () {
    return gulp.src(appDev + '**/*.ts')
        .pipe(sourcemaps.init())
        .pipe(typescript(tsProject))
        .pipe(sourcemaps.write())
        // .pipe(jsuglify())
        .pipe(gulp.dest(appProd));
});

gulp.task('build-img', function () {
    return gulp.src(assetsDev + 'img/**/*')
        .pipe(imagemin({
            progressive: true
        }))
        .pipe(gulp.dest(assetsProd + 'img/'));
});

gulp.task('build-html', function () {
    return gulp.src(appDev + '**/*.html')
        .pipe(gulp.dest(appProd));
});

gulp.task('clean', function() {
    return del.sync(assetsProd);
});

gulp.task('clear', function () {
    return cache.clearAll();
});

gulp.task('watch', ['build-html','build-ts' , 'build-img', 'build-less' ] ,function () {
    gulp.watch(appDev + '**/*.ts', ['build-ts']);
    gulp.watch(assetsDev + 'img/*', ['build-img']);
    gulp.watch(appDev + '**/*.html', ['build-html'], browserSync.reload);
    gulp.watch(assetsDev + 'less/**/*.less', ['build-less']);
    gulp.watch(appDev + '**/*.less', ['build-less-in-dev']);
});

gulp.task('default', ['watch']);
