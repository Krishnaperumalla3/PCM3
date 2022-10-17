
var exec = require('child_process').exec;
var gulp = require('gulp');
var gzip = require('gulp-gzip');

var rimraf = require('rimraf');


gulp.task('build-clean',function(done){
    rimraf.sync('./dist');
    console.log('dist deleted done');
    rimraf.sync('../src/main/resources/static/*');
    console.log('spring boot static deleted !');
    done();
});


gulp.task('cleanup',function(done){
    rimraf.sync('./dist');
    console.log('dist deleted done');
    rimraf.sync('../bin/*');
    console.log('spring boot static deleted !');
    rimraf.sync('../bin/*');
    console.log('community-manager-api bin deleted !');
    rimraf.sync('../../community-manager-common/bin/*');
    console.log('community-manager-common bin deleted !');
    rimraf.sync('../../community-manager-repository/bin/*');
    console.log('community-manager-repository bin deleted !');
    rimraf.sync('../../community-manager-service/bin/*');
    console.log('community-manager-service bin deleted !');
    
    done();
});


gulp.task('build-run',gulp.series(gulp.parallel('build-clean'), function (done) {
    console.log('build-run !');
    buildCommand ='npm run build:Prod';
    exec(buildCommand, function (err, stdout, stderr) {
        console.log(stdout);
        console.log(stderr);
        if(err){
            done(err);
        }else{
            done();
        }
    });
    console.log('executing :' +buildCommand);
}));

gulp.task('build-copy',gulp.series(gulp.parallel('build-run'), function (done) {
    gulp.src('./dist/Demo/**/*')
        .pipe(gulp.dest('../src/main/resources/static'));
    done();
    console.log('copy build to dist');
}));


gulp.task('default',gulp.series(gulp.parallel('build-copy'), function (done) {
    console.log('deploy completed');
    done();
}));

