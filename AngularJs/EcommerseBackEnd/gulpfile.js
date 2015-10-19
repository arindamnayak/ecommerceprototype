var gulp = require('gulp'),
	browserSync = require('browser-sync').create(),
	bowerFiles = require('main-bower-files'),
    inject = require('gulp-inject'),
    stylus = require('gulp-stylus'),
    es = require('event-stream'),
	livereload = require('gulp-livereload');


gulp.task('inject', function() {
	//console.log(bowerFiles());
	//return gulp.src('./web/index.html')
	//.pipe(inject(gulp.src(bowerFiles(), {read: false}), {name: 'bower'}))
	//.pipe(inject(gulp.src(['web/app/app.js', 'web/app/routes/routes.js'],{read: false})))
	//.pipe(gulp.dest('./web'));
});	

gulp.task('watch', function() {
	//livereload.listen();
	//gulp.watch("./web/**/*.js", browserSync.reload);
	//gulp.watch("./web/**/*.html", browserSync.reload);
});


// Static server
gulp.task('serve', ['inject', 'watch'] , function() {
    browserSync.init({
        server: {
            baseDir: "./"
        }
    });
});