import argparse


parser = argparse.ArgumentParser(description='Revision arg parse')
parser.add_argument('-e','--echo',help='Print some text option')
parser.add_argument('-sq','--sq',help='Get square root',default=4,type=int)
args =parser.parse_args()

print parser.print_help()

print '---------------'
if args.echo:
	print args.echo

# if args.sq:
# 	print args.sq**2