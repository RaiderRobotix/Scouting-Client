fields = ['teamNum','teamName','avgAutoScore', 'sdAutoScore','avgTeleOpScore',
           'sdTeleOpScore','avgMatchScore','sdMatchScore',
          'reachBaselinePercentage','avgAutoKpa', 'sdAutoKpa','avgTeleOpKpa', 'sdTeleOpKpa',
          'avgAutoGears','sdAutoGears','avgTeleOpGears', 'sdTeleOpGears',
          'avgHighGoals','sdHighGoals','avgLowGoals',
          'sdLowGoals',
          'avgHoppers','avgPointsPerCycle','sdPointsPerCycle','avgCycles',
          'sdCycles',
          'takeoffPercentage','takeoffAttemptPercentage','takeoffAttemptSuccessPercentage',
          'pilotPlayPercentage', 'autoShootsKey',
            'frequentRobotCommentStr','frequentPilotCommentStr',
          'allComments']

header = 'String header = \"'
for field in fields:
    header+= field+','
header+='\\n\";'
fileContents = 'fileContents += '
for field in fields:
    fileContents+='report.'+field+'+COMMA+'

fileContents+='+\'\\n\';'

print(header)
print('\n')
print(fileContents)
