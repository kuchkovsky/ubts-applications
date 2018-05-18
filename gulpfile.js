const gulp = require('gulp');
const babel = require('gulp-babel');
const concat = require('gulp-concat');
const ngAnnotate = require('gulp-ng-annotate');
const plumber = require('gulp-plumber');
const uglify = require('gulp-uglify');
const bytediff = require('gulp-bytediff');

gulp.task('lib', function() {
    return gulp.src('src/main/resources/static/js/lib/*.js')
        .pipe(plumber())
        .pipe(concat('vendor.bundle.js'))
        .pipe(ngAnnotate({ add: true }))
        .pipe(uglify({ mangle: false }))
        .pipe(plumber.stop())
        .pipe(gulp.dest('target/classes/static/js/'));
});

gulp.task('dev', ['lib'], function() {
    return gulp.src(['src/main/resources/static/js/app.js', 'src/main/resources/static/js/**/*.js', '!**/lib/*.js'])
        .pipe(plumber())
        .pipe(concat('app.bundle.js'))
        .pipe(babel({ presets: ['env'] }))
        .pipe(ngAnnotate({ add: true }))
        .pipe(plumber.stop())
        .pipe(gulp.dest('target/classes/static/js/'));
});

gulp.task('prod', ['dev'], function() {
    return gulp.src('target/classes/static/js/app.bundle.js')
        .pipe(plumber())
        .pipe(bytediff.start())
        .pipe(uglify({ mangle: false }))
        .pipe(bytediff.stop())
        .pipe(plumber.stop())
        .pipe(gulp.dest('target/classes/static/js/'));
});
