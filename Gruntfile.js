module.exports = function(grunt)
{
    require("load-grunt-tasks")(grunt);

    grunt.initConfig({
        babel: {
            options: {
                presets: ['env']
            },
            dist: {
                files: [
                    {
                        expand: true,
                        cwd: 'src/main/resources/static/js',
                        src: ['**/*.js', '!lib/*'],
                        dest: 'target/classes/static/js'
                    }
                ]
            }
        }
    });

    grunt.registerTask('default', ['babel']);

};
